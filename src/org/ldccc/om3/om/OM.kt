package org.ldccc.om3.om

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field
import java.sql.*
import java.sql.Date
import java.util.*

open class OM<O : PO> protected constructor(val clazz: Class<O>, val base: String) {
  val fields: Array<Field> = clazz.declaredFields
  var columns: Array<String>
  
  init {
    columns = fields.map(Aide::field2column).toTypedArray()
    fields.forEach { it.isAccessible = true }
  }
  
  fun newO1(block: (O) -> Unit): O {
    val o :O = clazz.newInstance()
    block(o)
    return o
  }
  
  fun newO2(block: (O) -> O): O {
    return block(clazz.newInstance())
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
    return ArrayList<O>().apply {
      source.connection.use {
        it.createStatement().use {
          it.executeQuery(sql).use { while (it.next()) this.add(getO(getMap(it, it.metaData))) }
        }
      }
    }
  }
  
  open fun add(sql: String): Boolean {
    return source.connection.use {
      it.createStatement().use {
        it.executeUpdate(sql) != 0
      }
    }
  }
  
  open fun update(sql: String): Boolean {
    return source.connection.use {
      it.createStatement().use {
        it.executeUpdate(sql) != 0
      }
    }
  }
  
  open fun delete(sql: String): Boolean {
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
