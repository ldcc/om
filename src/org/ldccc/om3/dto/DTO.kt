package org.ldccc.om3.dto

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

	companion object {
		val ID: String = "ID"
	}
}
