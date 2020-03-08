package org.ldcc.om.core

import org.ldcc.om.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*
import kotlin.collections.HashMap

open class Engine<O : PO> constructor(private val clazz: Class<O>, private val table: String) {
    private val fields: Array<Field> = clazz.declaredFields
    private var columns: Array<String> = fields.map(Utils::field2column).toTypedArray()

    init {
        fields.forEach { it.isAccessible = true }
    }

    protected open fun getO(map: Map<String, Any?>): O {
        return clazz.newInstance().apply {
            for (i in fields.indices) fields[i].let {
                if (it.get(this) !is PO) it.set(this, map[columns[i]])
            }
        }
    }

    protected open fun getMap(set: ResultSet, data: ResultSetMetaData): Map<String, Any?> {
        return HashMap<String, Any?>().apply {
            for (i in 1..data.columnCount) when (data.getColumnClassName(i)) {
                String::class.java.name -> this[data.getColumnLabel(i)] = set.getString(i)
                Int::class.java.name -> this[data.getColumnLabel(i)] = set.getInt(i)
                Date::class.java.name -> this[data.getColumnLabel(i)] = set.getDate(i)
                Boolean::class.java.name -> this[data.getColumnLabel(i)] = set.getBoolean(i)
                else -> this[data.getColumnLabel(i)] = set.getObject(i)
            }
        }
    }

    private val source = Source.getSingleton()

    private fun select(sql: String): List<O> {
        println(sql)
        return ArrayList<O>().apply {
            source.connection.use {
                it.createStatement().use {
                    it.executeQuery(sql).use {
                        while (it.next()) {
                            this.add(getO(getMap(it, it.metaData)))
                        }
                    }
                }
            }
        }
    }

    private fun insert(sql: String): Boolean {
        println(sql)
        return source.connection.use {
            it.createStatement().use {
                it.executeUpdate(sql) != 0
            }
        }
    }

    private fun update(sql: String): Boolean {
        println(sql)
        return source.connection.use {
            it.createStatement().use {
                it.executeUpdate(sql) != 0
            }
        }
    }

    private fun delete(sql: String): Boolean {
        println(sql)
        return source.connection.use {
            it.createStatement().use {
                it.executeUpdate(sql) != 0
            }
        }
    }

    private inline fun <T> Connection.use(block: (Connection) -> T): T {
        return try {
            block(this)
        } finally {
            source.closeConnection(this)
        }
    }

    private inline fun <T> Statement.use(block: (Statement) -> T): T {
        return try {
            block(this)
        } finally {
            this.close()
        }
    }

    private inline fun <T> ResultSet.use(block: (ResultSet) -> T): T? {
        return try {
            block(this)
        } finally {
            this.close()
        }
    }

    private fun new1(block: O.() -> Unit): O {
        val o: O = clazz.newInstance()
        block(o)
        return o
    }

    private fun new2(block: O.() -> O): O {
        return block(clazz.newInstance())
    }

    private fun serialWhere(block: O.() -> Unit): String {
        return Utils.whereStatement(fields, columns, new1(block))
    }


    /**
     *
     * the following interface that is the openup to use
     *
     */

    /**
     * return a sql-sentence that could inquire all object completely from the table
     */
    fun selectall(): SQL {
        return SQL("SELECT ${columns.joinToString()} FROM $table")
    }

    /**
     * inquire and return a single complete object
     */
    fun select1(block: O.() -> Unit): O? {
        val sql = selectall().apply { sql.append(" WHERE ${serialWhere(block)}") }.limit(1)
        return sql.execute().getOrNull(0)
    }

    /**
     * inquire and return a series object with common sql-sentence by using functional
     */
    fun selectf(where: SQL.() -> SQL): List<O> {
        return selectall().apply { where(this.append(" WHERE ")) }.execute()
    }

    /**
     * return a sql-sentence that could inquire a series specify object
     */
    fun selectm(block: O.() -> Unit): SQL {
        return selectall().apply { sql.append(" WHERE ${serialWhere(block)}") }
    }

    /**
     * insert a given object to the table
     * return true if successful
     */
    fun insert(block: O.() -> Unit): Boolean {
        val sql = "INSERT INTO $table ${Utils.insStatement(fields, columns, new1(block))}"
        return insert(sql)
    }

    /**
     * modify elements of an object according by the givien identity
     * return true if successful
     */
    fun update(block: O.() -> Unit): Boolean {
        val sql = "UPDATE $table SET ${Utils.udtStatement(fields, columns, new1(block))}"
        return update(sql)
    }

    /**
     * remove a whole object according by the given identity
     * return true if successful
     */
    fun delete(block: O.() -> Unit): Boolean {
        val sql = "DELETE FROM $table WHERE ${Utils.delStatement(new1(block))}"
        return delete(sql)
    }

    inner class SQL(val sql: StringBuilder) {
        constructor() : this(StringBuilder())
        constructor(sql: String) : this(StringBuilder(sql))

        fun append(str: String): SQL = apply { sql.append(str) }

        infix fun String.eq(eq: Any): SQL = append("$this = ${Utils.sign(eq)}")

        infix fun String.regexp(regexp: String): SQL = append("$this REGEXP '$regexp'")

        infix fun eq(eq: Any): SQL = append(" = ${Utils.sign(eq)}")

        infix fun regexp(regexp: String): SQL = append(" REGEXP '$regexp'")

        infix fun and(and: String): SQL = append(" && $and")

        infix fun or(or: String): SQL = append(" || $or")

        infix fun limit(limit: Int): SQL = append(" LIMIT $limit")

        infix fun offset(offset: Int): SQL = append(" OFFSET $offset")

        infix fun orderBy(by: String): SQL = append(" ORDER BY $by")

        fun orderBy(vararg by: String) = apply { sql.append(" ORDER BY ${by.joinToString()}") }

        fun execute(): List<O> = select(sql.toString())
    }
}
