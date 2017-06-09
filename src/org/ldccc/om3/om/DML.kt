package org.ldccc.om3.om

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*

open class DML<O : PO> constructor(val clazz: Class<O>, val base: String) {
  val fields: Array<Field> = clazz.declaredFields
  var columns: Array<String>
  
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
  
  open fun select(sql: String): List<O> {
    println(sql)
    return ArrayList<O>().apply {
      source.connection.use {
        it.createStatement().use {
          it.executeQuery(sql).use { while (it.next()) this.add(getO(getMap(it, it.metaData))) }
        }
      }
    }
  }
  
  open fun add(sql: String): Boolean {
    println(sql)
    return source.connection.use {
      it.createStatement().use {
        it.executeUpdate(sql) != 0
      }
    }
  }
  
  open fun update(sql: String): Boolean {
    println(sql)
    return source.connection.use {
      it.createStatement().use {
        it.executeUpdate(sql) != 0
      }
    }
  }
  
  open fun delete(sql: String): Boolean {
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
  fun selectAll(): Select {
    return Select("SELECT * FROM $base")
  }
  
  inline fun selectSingle(block: O.() -> Unit): O? {
    val sql = "SELECT * FROM $base WHERE ${Aide.selStatement(fields, columns, new1(block))};"
    return select(sql).getOrNull(0)
  }
  
  inline fun select(where: Select.() -> String): Select {
    return Select("SELECT * FROM $base WHERE ").apply { sql.append(where(this)) }
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
  
  inner class Select(val sql: StringBuilder) {
    constructor(sql: String):this(StringBuilder(sql))
    
    infix fun String.eq(value: Any): String {
      return "$this = %${Aide.sign(value)}%"
    }
    
    infix fun String.regexp(value: Any): String {
      return "$this REGEXP '$value'"
    }
    
    infix fun String.and(value: String): String {
      return "$this && $value"
    }
    
    infix fun String.or(value: String): String {
      return "$this || $value"
    }
    
    fun limit(limit: Int, offset: Int = 0): Select {
      return apply { sql.append(" LIMIT $limit OFFSET $offset") }
    }
    
    fun orderBy(vararg by: String): Select {
      return apply { sql.append(" ORDER BY ${by.joinToString()}") }
    }
    
    fun execute() = select(sql.toString())
  }
  
}
