package org.ldccc.om3.dto

import java.util.*

open class DTO {
	var map = HashMap<String, Any>()

	constructor(map: HashMap<String, Any>)
	constructor()

	override fun toString(): String {
		return map.toString()
	}

	override fun hashCode(): Int {
		val keyCode = map.keys.stream().map(String::hashCode).reduce(Int::compareTo).orElse(0)
		val valueCode = map.values.stream().map(Any::hashCode).reduce(Int::compareTo).orElse(0)
		return (keyCode + valueCode) * 31
	}

	override fun equals(other: Any?): Boolean {
		return other!!.javaClass == this.javaClass && this.hashCode() == other.hashCode()
	}
}
