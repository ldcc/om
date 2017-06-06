package org.ldccc.om3.dbm

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

	fun blurStatement(base: String, columns: Array<String>, conds: Array<String>): String {
		val cond = columns.indices.map { columns[it] + " LIKE '%" + conds[it] + "%'" }.joinToString(" && ")
		return "SELECT * FROM $base WHERE $cond"
	}

	fun regStatement(base: String, columns: Array<String>, regs: Array<String>): String {
		val cond = columns.indices.map { columns[it] + " REGEXP '" + regs[it] + "'" }.joinToString(" && ")
		return "SELECT * FROM $base WHERE $cond"
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
		val cols: String = columns.indices.map { i ->
			fields[i].get(o).let {
				columns[i] + "=" + when {
					it is PO -> o.boxing(it)
					it != null -> sign(it)
					else -> "null"
				}
			}
		}.joinToString()
		return " SET $cols WHERE ID=${o.id};"
	}

	fun <O : PO> delStatement(o: O): String = " WHERE ID = ${o.id};"
}

