package dto

import constant.C.DTGT
import constant.GT
import org.ldccc.om3.dbm.Aide
import org.ldccc.om3.dbm.OMer
import org.ldccc.om3.dbm.OM
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
		var gGroundingDate: Date?,
		var gType: GType?
) : PO(id) {
	constructor() : this(0, null, null, null, null, null, null, null, null, GType())

	override fun <O : PO> boxing(o: O): Any {
		return when (o) {
			is GType -> Aide.sign(o.gtCode as String)
			else -> "null"
		}
	}
}

class DTG<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base) {
	init {
		columns[9] = "gt_code"
	}
	override fun getO(map: Map<String, Any>): O {
		val o: Good = super.getO(map) as Good
		o.gType = OMer.getSingleton().findBy(DTGT, GT.gt_code.name, map[GT.gt_code.name].toString())
		return o as O
	}
}