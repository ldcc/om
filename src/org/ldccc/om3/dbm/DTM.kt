package org.ldccc.om3.dbm

import org.ldccc.om3.dto.DTO

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.sql.Date
import java.sql.*
import java.util.*

abstract class DTM<O : DTO> protected constructor(private val clazz: Class<O>, private val base: String) {
	private val fields: Array<Field> = clazz.declaredFields
	private val classes: Array<Class<*>>

	var columns: Array<String>

	init {
		columns = fields.map(Aide::field2column).toTypedArray()
		classes = fields.map(Field::getType).toTypedArray()
		fields.forEach { it.isAccessible = true }
	}

	private fun getO(map: Map<String, Any>): O {
		return try {
			clazz.getConstructor(*classes).newInstance(
					*columns.map(map::get).toTypedArray()
			)
		} catch (e: InstantiationException) {
			e.printStackTrace()
			clazz.newInstance()
		} catch (e: IllegalAccessException) {
			e.printStackTrace()
			clazz.newInstance()
		} catch (e: NoSuchMethodException) {
			e.printStackTrace()
			clazz.newInstance()
		} catch (e: InvocationTargetException) {
			e.printStackTrace()
			clazz.newInstance()
		}
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

	fun findByID(statement: Statement, column: String, value: String): O {
		val sql = "SELECT * FROM $base WHERE $column='$value';"
		return findByID(statement, sql) as O
	}

	fun findByCondition(statement: Statement): List<O> {
		val sql = "SELECT * FROM $base;"
		return findByCondition(statement, sql) as List<O>
	}

	fun add(statement: Statement, vararg os: O): Boolean {
		val sql = "INSERT INTO " + base + Aide.toInsertSQL(fields, columns, *os)
		return add(statement, sql)
	}

	fun update(statement: Statement, o: O): Boolean {
		val sql = "UPDATE " + base + Aide.toUpdateSQL(fields, columns, o)
		return update(statement, sql)
	}

	fun delete(statement: Statement, vararg os: O): Boolean {
		val sql = "DELETE FROM " + base + Aide.toDeleteSQL(*os)
		return delete(statement, sql)
	}

	protected fun findByID(statement: Statement, sql: String): O? {
		return try {
			statement.executeQuery(sql).use {
				it.first()
				getO(getMap(it, it.metaData))
			}
		} catch (e: SQLException) {
			e.printStackTrace()
			null
		}
	}

	protected fun findByCondition(statement: Statement, sql: String): List<O>? {
		return try {
			statement.executeQuery(sql).use {
				val os = ArrayList<O>()
				while (it.next()) os.add(getO(getMap(it, it.metaData)))
				os
			}
		} catch (e: SQLException) {
			e.printStackTrace()
			null
		}
	}

	protected fun add(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			System.err.println("\nuser name is already used\n")
			e.printStackTrace()
			false
		}
	}

	protected fun update(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			e.printStackTrace()
			false
		}
	}

	protected fun delete(statement: Statement, sql: String): Boolean {
		return try {
			statement.executeUpdate(sql) != 0
		} catch (e: SQLException) {
			e.printStackTrace()
			false
		}
	}
}

fun <O> ResultSet.use(block: (ResultSet) -> O): O? {
	return try {
		block(this)
	} finally {
		this.close()
	}
}