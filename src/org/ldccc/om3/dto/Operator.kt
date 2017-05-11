package org.ldccc.om3.dto

import java.lang.reflect.Field

object Operator {
//	fun <O> setVal(column: String, value: O) {
//		map.put(column, value)
//	}
//
//	fun <O> getVal(column: String): O {
//		return map.get(column)
//	}


	private fun sqlStyle(ori: Any): String {
		return when (ori) {
			is Boolean, is Int, is Long -> ori.toString()
			else -> "\'" + ori + "\'"
		}
	}

	fun <O : DTO> toInsertSQL(o: O, fields: Array<Field>, params: Array<String>): String {
		return "(${params.joinToString()})VALUES(${fields.map { sqlStyle(it.get(o)) }.joinToString()});"
	}

	fun <O : DTO> toUpdateSQL(o: O, fields: Array<Field>, params: Array<String>): String {
		return " SET ${params.indices.map { params[it] + "=" + sqlStyle(fields[it].get(o)) }.joinToString()} WHERE ID=${o.id};"
	}

	fun <O : DTO> toDeleteSQL(o: O): String {
		return " WHERE ID=${o.id};"
	}
}