package org.ldccc.om3.om

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field


object Aide {
  
  fun field2column(field: Field) = camel2snake(field.name)
  
  fun camel2snake(arg: String): String {
    return arg.toCharArray().map {
      if (it.isUpperCase().not()) it.toString() else "_" + it.toLowerCase().toString()
    }.reduce { acc, s -> acc + s }
  }
  
  fun sign(value: Any): String {
    return when (value) {
      is Boolean, is Int, is Long -> value.toString()
      else -> "\'" + value + "\'"
    }
  }
  
  fun <O : PO> Field.attr(o: O): String {
    return get(o).run {
      when (this) {
        is PO -> o.boxing(this)
        else -> sign(this)
      }
    }
  }
  
  fun <O : PO> selStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    return columns.indices.filter {
      fields[it].get(o) != null
    }.map {
      "${columns[it]} = " + fields[it].attr(o)
    }.joinToString(" && ")
  }
  
  fun StringBuilder.join(str: String): StringBuilder {
    return if (isEmpty()) append(str) else append(",$str")
  }
  
  fun <O : PO> insStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    val cols = StringBuilder()
    val cond = StringBuilder()
    columns.indices.filter {
      fields[it].get(o) != null
    }.forEach {
      cols.join(columns[it])
      cond.join(fields[it].attr(o))
    }
    return "($cols)VALUES($cond);"
  }
  
  fun <O : PO> updStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    val cols: String = columns.indices.filter {
      fields[it].get(o) != null
    }.map {
      "${columns[it]} = ${fields[it].attr(o)}"
    }.joinToString()
    return "$cols WHERE ID=${o.id};"
  }
  
  fun <O : PO> delStatement(o: O): String = "id = ${o.id};"
}

