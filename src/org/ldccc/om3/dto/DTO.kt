package org.ldccc.om3.dto

import java.lang.reflect.Field

open class DTO(open var id: Int) {
	constructor() : this(0)

//	override fun hashCode(): Int {
//		val keyCode = map.keys.stream().map(String::hashCode).reduce(Int::compareTo).orElse(0)
//		val valueCode = map.values.stream().map(Any::hashCode).reduce(Int::compareTo).orElse(0)
//		return (keyCode + valueCode) * 31
//		return 0
//	}

//	override fun equals(other: Any?): Boolean {
//		return other!!.javaClass == this.javaClass && this.hashCode() == other.hashCode()
//	}


	private fun sqlStyle(ori: Any): String = when (ori) {
		is Boolean, is Int, is Long -> ori.toString()
		else -> "\'" + ori + "\'"
	}

	fun toInsertSQL(fields: Array<Field>, params: Array<String>) = "(${params.joinToString()})VALUES(${fields.map { sqlStyle(it.get(this)) }.joinToString()});"

	fun toUpdateSQL(fields: Array<Field>, params: Array<String>) = " SET ${params.indices.map { params[it] + "=" + sqlStyle(fields[it].get(this)) }.joinToString()} WHERE ID=${this.id};"

	fun toDeleteSQL() = " WHERE ID=${this.id};"

	companion object {
		val ID: String = "ID"
	}
}