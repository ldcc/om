package org.ldcc.martin.dto

import constant.C.dtgt
import constant.GT
import org.ldccc.om3.dbm.DBManager
import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.PO
import java.sql.Date

data class Good(
		override var id: Int,
		var gTitle: String?,
		var gBrand: String?,
		var gPrice: Double?,
		var gFavorablePrice: Double?,
		var gStorage: Int?,
		var gImage: String?,
		var gDescription: String?,
		var goodsType: GoodType?,
		var gGroundingDate: Date?
) : PO(id) {
	constructor():this(0,null,null,null,null,null,null,null,null,null)
}

class DTG<O : PO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base) {
	override fun getO(map: Map<String, Any>): O {
		val o: Good = super.getO(map) as Good
		o.goodsType = DBManager.getSingleton().findBy(dtgt, GT.gt_code.name, map[GT.gt_code.name].toString())
		return o as O
	}
}