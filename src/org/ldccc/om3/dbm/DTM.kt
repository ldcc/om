package org.ldccc.om3.dbm

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*

open class DTM<O : PO> protected constructor(protected val clazz: Class<O>, private val base: String) {
	protected val fields: Array<Field> = clazz.declaredFields
	protected var columns: Array<String>

	init {
		columns = fields.map(Aide::field2column).toTypedArray()
		fields.forEach { it.isAccessible = true }
	}

	protected open fun getO(map: Map<String, Any>): O {
		val o: O = clazz.newInstance()
		fields.indices.forEach { fields[it].set(o, map[columns[it]]) }
		return o
	}

	@Throws(SQLException::class)
	private fun getMap(set: ResultSet, data: ResultSetMetaData): Map<String, Any> {
		val map = HashMap<String, Any>()
		for (i in 1..data.columnCount) {
			val clsName = data.getColumnClassName(i)
			if (clsName == String::class.java.name) {
				map.put(data.getColumnLabel(i), set.getString(i))
			} else if (clsName == Int::class.java.name) {
				map.put(data.getColumnLabel(i), set.getInt(i))
			} else if (clsName == Date::class.java.name) {
				map.put(data.getColumnLabel(i), set.getDate(i))
			} else if (clsName == Boolean::class.java.name) {
				map.put(data.getColumnLabel(i), set.getBoolean(i))
			} else {
				map.put(data.getColumnLabel(i), set.getObject(i))
			}
		}
		return map
	}

	protected open fun findBy(statement: Statement, column: String, value: String): O? {
		val sql = "SELECT * FROM $base WHERE $column=${Aide.sign(value)};"
		println(sql)
		return findBy(statement, sql)
	}

	protected open fun findAll(statement: Statement): List<O>? {
		val sql = "SELECT * FROM $base;"
		println(sql)
		return findWithCond(statement, sql)
	}

	protected open fun add(statement: Statement, vararg os: O): Boolean {
		val sql = "INSERT INTO " + base + Aide.insStatement(fields, columns, *os)
		println(sql)
		return add(statement, sql)
	}

	protected open fun update(statement: Statement, o: O): Boolean {
		val sql = "UPDATE " + base + Aide.updStatement(fields, columns, o)
		println(sql)
		return update(statement, sql)
	}

	protected open fun delete(statement: Statement, vararg os: O): Boolean {
		val sql = "DELETE FROM " + base + Aide.delStatement(*os)
		println(sql)
		return delete(statement, sql)
	}

	protected open fun findBy(statement: Statement, sql: String): O? {
		return try {
			statement.executeQuery(sql).use { if (it.first()) getO(getMap(it, it.metaData)) else null }
		} catch (e: SQLException) {
			e.printStackTrace()
			null
		}
	}

	protected open fun findWithCond(statement: Statement, sql: String): List<O>? {
		return try {
			val os = ArrayList<O>()
			statement.executeQuery(sql).use { while (it.next()) os.add(getO(getMap(it, it.metaData))) }
			os
		} catch (e: SQLException) {
			e.printStackTrace()
			null
		}
	}

	protected open fun add(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			System.err.println("\ndatabase column name or value do not matches\n")
			e.printStackTrace()
			false
		}
	}

	protected open fun update(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			e.printStackTrace()
			false
		}
	}

	protected open fun delete(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			e.printStackTrace()
			false
		}
	}

	private fun <O> ResultSet.use(block: (ResultSet) -> O): O? {
		return try {
			block(this)
		} finally {
			this.close()
		}
	}

}
