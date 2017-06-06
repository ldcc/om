package org.ldccc.om3.dto

open class PO(open var id: Int) {
	constructor() : this(0)

//	override fun hashCode(): Int {
//		val keyCode = map.keys.stream().map(String::hashCode).reduce(Int::compareTo).orElse(0)
//		val valueCode = map.values.stream().map(Any::hashCode).reduce(Int::compareTo).orElse(0)
//		return (keyCode + valueCode) * 31
//	}

//	override fun equals(other: Any?): Boolean {
//		return other!!.javaClass == this.javaClass && this.hashCode() == other.hashCode()
//	}


//	private fun sqlStyle(ori: Any): String = when (ori) {
//		is Boolean, is Int, is Long -> ori.toString()
//		else -> "\'" + ori + "\'"
//	}
//
//	fun insStatement(fields: Array<Field>, columns: Array<String>): String {
//		return "(${columns.joinToString()})VALUES(${fields.map { sqlStyle(it.get(this)) }.joinToString()});"
//	}
//
//	fun updStatement(fields: Array<Field>, columns: Array<String>): String {
//		return " SET ${columns.indices.map { columns[it] + "=" + sqlStyle(fields[it].get(this)) }.joinToString()} WHERE ID=${this.id};"
//	}
//
//	fun delStatement() = " WHERE ID=${this.id};"

	companion object {
		val ID: String = "ID"
	}

	open fun <O : PO> boxing(o: O) = Any()
}
