package org.ldccc.om3.dbm

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*

open class OM<O : PO> protected constructor(protected val clazz: Class<O>, private val base: String) {
	protected val fields: Array<Field> = clazz.declaredFields
	protected var columns: Array<String>

	init {
		columns = fields.map(Aide::field2column).toTypedArray()
		fields.forEach { it.isAccessible = true }
	}

	protected open fun getO(map: Map<String, Any>): O {
		return clazz.newInstance().apply {
			for (i in 0..fields.size - 1) fields[i].let {
				if (it.get(this) !is PO) it.set(this, map[columns[i]])
			}
		}
	}

	@Throws(SQLException::class)
	private fun getMap(set: ResultSet, data: ResultSetMetaData): Map<String, Any> {
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

	protected open fun findSingle(column: String, value: String): O? {
		val sql = "SELECT * FROM $base WHERE $column=${Aide.sign(value)};"
		return findSingle(sql)
	}

	protected open fun findsBy(column: String, value: String): List<O>? {
		val sql = "SELECT * FROM $base WHERE $column=${Aide.sign(value)};"
		return find(sql)
	}

	protected open fun findAll(): List<O>? {
		val sql = "SELECT * FROM $base;"
		return find(sql)
	}

	protected open fun add(o: O): Boolean {
		val sql = "INSERT INTO " + base + Aide.insStatement(fields, columns, o)
		return add(sql)
	}

	protected open fun update(o: O): Boolean {
		val sql = "UPDATE " + base + Aide.updStatement(fields, columns, o)
		return update(sql)
	}

	protected open fun delete(o: O): Boolean {
		val sql = "DELETE FROM " + base + Aide.delStatement(o)
		return delete(sql)
	}


	private val source = Source.getSingleton()

	protected open fun findSingle(sql: String): O? {
		println(sql)
		return source.connection.use {
			it.createStatement().use {
				it.executeQuery(sql).use { if (it.first()) getO(getMap(it, it.metaData)) else null }
			}
		}
	}

	protected open fun find(sql: String): List<O> {
		println(sql)
		return ArrayList<O>().apply {
			source.connection.use {
				it.createStatement().use {
					it.executeQuery(sql).use { while (it.next()) this.add(getO(getMap(it, it.metaData))) }
				}
			}
		}
	}

	protected open fun add(sql: String): Boolean {
		println(sql)
		return source.connection.use {
			it.createStatement().use {
				it.executeUpdate(sql) != 0
			}
		}
	}

	protected open fun update(sql: String): Boolean {
		println(sql)
		return source.connection.use {
			it.createStatement().use {
				it.executeUpdate(sql) != 0
			}
		}
	}

	protected open fun delete(sql: String): Boolean {
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

}
