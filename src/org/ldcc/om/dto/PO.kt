package org.ldcc.om.dto

open class PO(open var id: Int?) {
    constructor() : this(null)

    companion object {
        const val ID: String = "ID"
    }

    open fun <O : PO> boxing(o: O) = String()
}
