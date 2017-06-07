package org.ldccc.om3.om

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field


object Aide {
  fun field2column(field: Field) = camel2snake(field.name)
  
  fun camel2snake(arg: String) = arg.toCharArray().map { if (it.isUpperCase().not()) it.toString() else "_" + it.toLowerCase().toString() }.reduce(Aide::concat)
  
  private fun concat(s1: String, s2: String) = s1 + s2
  
  fun sign(value: Any): String {
    return when (value) {
      is Boolean, is Int, is Long -> value.toString()
      else -> "\'" + value + "\'"
    }
  }

//  fun blurStatement(base: String, columns: Array<String>, conds: Array<String>): String {
//    val cond = columns.indices.map { columns[it] + " LIKE '%" + conds[it] + "%'" }.joinToString(" && ")
//    return "SELECT * FROM $base WHERE $cond"
//  }

//  fun regStatement(base: String, columns: Array<String>, regs: Array<String>): String {
//    val cond = columns.indices.map { columns[it] + " REGEXP '" + regs[it] + "'" }.joinToString(" && ")
//    return "SELECT * FROM $base WHERE $cond"
//  }
  
  fun <O : PO> selStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    return columns.indices.filter {
      fields[it].get(o) != null
    }.map {
      "${columns[it]} = " + fields[it].get(o).apply {
        when (this) {
          is PO -> o.boxing(this)
          else -> sign(this)
        }
      }
    }.joinToString()
  }
  
  fun <O : PO> insStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    val cols: String = columns.joinToString()
    val cond: String = fields.map {
      it.get(o).let {
        when {
          it is PO -> o.boxing(it)
          it != null -> sign(it)
          else -> "null"
        }
      }
    }.joinToString()
    return "($cols)VALUES($cond);"
  }
  
  fun <O : PO> updStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
    val cols: String = columns.indices.filter {
      fields[it].get(o) != null
    }.map {
      "${columns[it]} = " + fields[it].get(o).apply {
        when (this) {
          is PO -> o.boxing(this)
          else -> sign(this)
        }
      }
    }.joinToString()
    return "$cols WHERE ID=${o.id};"
  }
  
  fun <O : PO> delStatement(o: O): String = "ID = ${o.id};"
}

