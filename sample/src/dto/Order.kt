package org.ldcc.martin.dto

import constant.C
import dto.User
import org.ldccc.om3.dbm.Aide
import org.ldccc.om3.dbm.DBManager
import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.PO
import java.sql.Timestamp

data class Order(
		override var id: Int,
		var oCode: String?,
		var oCreate: Timestamp?,
		var oSend: Timestamp?,
		var oStatus: Int?,
		var oAmount: Double?,
		var oPay: Boolean?,
		var user: User?
) : PO(id) {
	constructor() : this(0, null, null, null, null, null, null, null)
	constructor(code: String?, create: Timestamp?, amount: Double?, user: User?) : this(0, code, create, null, 0, amount, false, user)

	override fun <O : PO> boxing(o: O): Any {
		return when (o) {
			is User -> Aide.sign(o.id)
			else -> "null"
		}
	}
}

class DTOR<O : PO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base) {
	override fun getO(map: Map<String, Any>): O {
		val o: Order = super.getO(map) as Order
		o.user = DBManager.getSingleton().findById(C.dtu, map[C.ID].toString().toInt())
		return o as O
	}

	init {
		columns[7] = "u_id"
	}
}
