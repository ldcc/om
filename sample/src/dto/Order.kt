package org.ldcc.martin.dto

import constant.C
import dto.User
import org.ldccc.om3.dbm.DBManager
import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.PO
import java.sql.Date

data class Order(
		override var id: Int,
		var oCode: String?,
		var oCreate: Date?,
		var oSend: Date?,
		var oStatus: Boolean?,
		var oAmount: Double?,
		var oPay: Boolean?,
		var user: User?
) : PO(id) {
	constructor() : this(0, null, null, null, null, null, null, null)
}

class DTOR<O : PO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base) {
	override fun getO(map: Map<String, Any>): O {
		val o: Order = super.getO(map) as Order
//		Thread(Runnable {
			o.user = DBManager.getSingleton().findById(C.dtu,map[C.ID].toString().toInt())
//		}).start()
		return o as O
	}
}