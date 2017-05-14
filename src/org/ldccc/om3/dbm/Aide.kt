package org.ldccc.om3.dbm

import org.ldccc.om3.dto.DTO
import java.lang.reflect.Field


object Aide {

	fun field2column(field: Field) = camel2snake(field.name)

	fun camel2snake(arg: String) = arg.toCharArray()
			.map { if (it.isUpperCase().not()) it.toString() else "_" + it.toLowerCase().toString() }
			.reduce(Aide::concat)

	private fun concat(s1: String, s2: String) = s1 + s2

	private fun toSQL(o: Any) = { fields: Array<Field> ->
		{ trans: (Any) -> String -> fields.map { trans(it.get(o)) } }
	}

	private fun sqlStyle(value: Any): String {
		return when (value) {
			is Boolean, is Int, is Long -> value.toString()
			else -> "\'" + value + "\'"
		}
	}

	fun <O : DTO> toInsertSQL(fields: Array<Field>, columns: Array<String>, vararg os: O): String {
		val cols: String = columns.joinToString()

		val cond: String = os.map { toSQL(it)(fields)(Aide::sqlStyle) }.joinToString()

		return "($cols)VALUES($cond);"
	}

	fun <O : DTO> toUpdateSQL(fields: Array<Field>, columns: Array<String>, o: O): String {
		val cols: String = columns.indices.map { columns[it] + "=" + sqlStyle(fields[it].get(this)) }.joinToString()
		return " SET $cols WHERE ID=${o.id};"
	}

	fun <O : DTO> toDeleteSQL(vararg os: O): String {
		val cond: String = os.map { "WHERE ID=" + it.id }.joinToString()
		return " WHERE ID=$cond;"
	}

}

