package org.ldccc.om3.dto

import java.lang.reflect.Field

object Operator {
	fun setAttrs(fields: Array<Field>, params: Array<String>, target: Any, map: Map<String, Any>) {
		fields.indices.forEach { fields[it].set(target, map[params[it]]) }
	}

	fun putAttrs(fields: Array<Field>, params: Array<String>, target: Any): Map<String, Any> {
		return fields.indices.map { Pair<String, Any>(params[it], fields[it].get(target)) }.toMap()
	}

//	fun <O> setVal(column: String, value: O) {
//		map.put(column, value)
//	}
//
//	fun <O> getVal(column: String): O {
//		return map.get(column)
//	}


	private fun sqlStyle(o: Any): String {
		return when (o) {
			is Boolean, is Int, is Long -> o.toString()
			else -> "\'" + o + "\'"
		}
	}

	private fun toO(field: Field, o: Any): String {
		field.isAccessible = true
		return sqlStyle(field.get(o))
	}

	fun <O : DTO> toInsertSQL(o: O, fields: Array<Field>, params: Array<String>): String {
		return "(${params.joinToString()})VALUES(${fields.map { toO(it, o) }.joinToString()});"
	}

//	fun toUpdateSQL(): String {
//		return " SET " +
//				map.keys.stream().map({ s -> s + "=" + this.sqlStyle(s) }).collect(Collectors.joining(",")) +
//				" WHERE " + ID + "=" + map.get(ID) + ";"
//	}
//
//	fun toDeleteSQL(): String {
//		return " WHERE " + ID + "=" + map.get(ID) + ";"
//	}
}