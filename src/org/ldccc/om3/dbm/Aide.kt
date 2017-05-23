package org.ldccc.om3.dbm

import org.ldccc.om3.dto.PO
import java.lang.reflect.Field


object Aide {
	fun field2column(field: Field) = camel2snake(field.name)

	fun camel2snake(arg: String) = arg.toCharArray()
			.map { if (it.isUpperCase().not()) it.toString() else "_" + it.toLowerCase().toString() }
			.reduce(Aide::concat)

	private fun concat(s1: String, s2: String) = s1 + s2

	fun sign(value: Any): String {
		return when (value) {
			is Boolean, is Int, is Long -> value.toString()
			else -> "\'" + value + "\'"
		}
	}

	fun bulrStatement(base: String, column: String, condition: String): String {
		return "SELECT * FROM $base WHERE $column LIKE '%$condition%'"
	}

	fun <O : PO> insStatement(fields: Array<Field>, columns: Array<String>, vararg os: O): String {
		val cols: String = columns.joinToString()
		val cond: String = os.map { o -> "(" + fields.map { sign(it.get(o)) }.joinToString() + ")" }.joinToString()
		return "($cols)VALUES$cond;"
	}

	fun <O : PO> updStatement(fields: Array<Field>, columns: Array<String>, o: O): String {
		val cols: String = columns.indices.map { columns[it] + "=" + sign(fields[it].get(o)) }.joinToString()
		return " SET $cols WHERE ID=${o.id};"
	}

	fun <O : PO> delStatement(vararg os: O): String {
		val cond: String = os.map { "ID=" + it.id }.joinToString("||")
		return " WHERE $cond;"
	}
}

