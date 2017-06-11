package org.ldccc.om.core

import org.ldccc.om.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*
import kotlin.collections.HashMap

open class DML<O : PO> constructor(val clazz: Class<O>, val base: String) {
	val fields: Array<Field> = clazz.declaredFields
	var columns: Array<String> = fields.map(Aide::field2column).toTypedArray()

	init {
		fields.forEach { it.isAccessible = true }
	}

	protected open fun getO(map: Map<String, Any>): O {
		return clazz.newInstance().apply {
			for (i in 0..fields.size - 1) fields[i].let {
				if (it.get(this) !is PO) it.set(this, map[columns[i]])
			}
		}
	}

	protected open fun getMap(set: ResultSet, data: ResultSetMetaData): Map<String, Any> {
		return HashMap<String, Any>().apply {
			for (i in 1..data.columnCount) when (data.getColumnClassName(i)) {
				String::class.java.name -> this.put(data.getColumnLabel(i), set.getString(i))
				Int::class.java.name -> this.put(data.getColumnLabel(i), set.getInt(i))
				Date::class.java.name -> this.put(data.getColumnLabel(i), set.getDate(i))
				Boolean::class.java.name -> this.put(data.getColumnLabel(i), set.getBoolean(i))
				else -> this.put(data.getColumnLabel(i), set.getObject(i))
			}
		}
	}

	private val source = Source.getSingleton()

	fun select(sql: String): List<O> {
		println(sql)
		return ArrayList<O>().apply {
			source.connection.use {
				it.createStatement().use {
					it.executeQuery(sql).use { while (it.next()) this.add(getO(getMap(it, it.metaData))) }
				}
			}
		}
	}

	fun add(sql: String): Boolean {
		println(sql)
		return source.connection.use {
			it.createStatement().use {
				it.executeUpdate(sql) != 0
			}
		}
	}

	fun update(sql: String): Boolean {
		println(sql)
		return source.connection.use {
			it.createStatement().use {
				it.executeUpdate(sql) != 0
			}
		}
	}

	fun delete(sql: String): Boolean {
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

	inline fun new1(block: O.() -> Unit): O {
		val o: O = clazz.newInstance()
		block(o)
		return o
	}

	inline fun new2(block: O.() -> O): O {
		return block(clazz.newInstance())
	}

	/**
	 * here is what you used
	 */
	fun selectAll(): SQL {
		return SQL("SELECT ${columns.joinToString()} FROM $base")
	}

	inline fun selectSingle(block: O.() -> Unit): O? {
		val sql = "SELECT ${columns.joinToString()} FROM $base WHERE ${Aide.whereStatement(fields, columns, new1(block))};"
		return select(sql).getOrNull(0)
	}

	inline fun select(block: O.() -> Unit): SQL {
		return SQL("SELECT ${columns.joinToString()} FROM $base WHERE ${Aide.whereStatement(fields, columns, new1(block))}")
	}

	inline fun select(from: String, where: SQL.() -> StringBuilder): List<O> {
		val sql = SQL("SELECT ${columns.joinToString()} FROM $from ").apply { sql.append(where(this)) }.sql.toString()
		return select(sql)
	}

	inline fun insert(block: O.() -> Unit): Boolean {
		val sql = "INSERT INTO $base ${Aide.insStatement(fields, columns, new1(block))}"
		return add(sql)
	}

	inline fun update(block: O.() -> Unit): Boolean {
		val sql = "UPDATE $base SET ${Aide.updStatement(fields, columns, new1(block))}"
		return update(sql)
	}

	inline fun delete(block: O.() -> Unit): Boolean {
		val sql = "DELETE FROM $base WHERE ${Aide.delStatement(new1(block))}"
		return delete(sql)
	}

	inner class SQL(val sql: StringBuilder) {
		constructor() : this(StringBuilder())
		constructor(sql: String) : this(StringBuilder(sql))

		infix fun String.from(from: String): StringBuilder = sql.append(" FROM $from")

		infix fun String.eq(eq: Any): StringBuilder = sql.append(" = %${Aide.sign(eq)}%")

		infix fun String.regexp(regexp: String): StringBuilder = sql.append(" REGEXP '$regexp'")

		infix fun StringBuilder.from(from: String): StringBuilder = sql.append(" FROM $from")

		infix fun StringBuilder.eq(eq: Any): StringBuilder = sql.append(" = ${Aide.sign(eq)}")

		infix fun StringBuilder.regexp(regexp: String): StringBuilder = sql.append(" REGEXP '$regexp'")

		infix fun StringBuilder.and(and: String): StringBuilder = sql.append(" && $and")

		infix fun StringBuilder.or(or: String): StringBuilder = sql.append(" || $or")

		infix fun StringBuilder.limit(limit: Int): StringBuilder = sql.append(" LIMIT $limit")

		infix fun StringBuilder.offset(offset: Int): StringBuilder = sql.append(" OFFSET $offset")

		infix fun StringBuilder.orderBy(by: String): StringBuilder = sql.append(" ORDER BY $by")


		fun limit(limit: Int) = apply { sql.append(" LIMIT $limit") }

		fun offset(offset: Int) = apply { sql.append(" OFFSET $offset") }

		fun orderBy(vararg by: String) = apply { sql.append(" ORDER BY ${by.joinToString()}") }

		fun execute(): List<O> = select(sql.toString())
	}
}
