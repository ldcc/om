package org.ldccc.om3.dto

open class PO(open var id: Int) {
	constructor() : this(0)
	
	companion object {
		val ID: String = "ID"
	}
	
	open fun <O : PO> boxing(o: O) = Any()
}
