package dto

import C
import GT
import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.Aide
import org.ldccc.om3.om.DML
import java.sql.Date

data class Good(override var id: Int?, var gTitle: String? = null, var gBrand: String? = null, var gPrice: Double? = null, var gFavorablePrice: Double? = null, var gStorage: Int? = null, var gImage: String? = null, var gDescription: String? = null, var gGroundingDate: Date? = null, var gType: GType? = GType()) : PO(id) {
	constructor() : this(0)

	override fun <O : PO> boxing(o: O): String {
		return when (o) {
			is GType -> Aide.sign(o.gtCode as String)
			else -> "null"
		}
	}
}

class DTG<O : PO> constructor(tClass: Class<O>, base: String) : DML<O>(tClass, base) {
	init {
		columns[9] = "gt_code"
	}

	@Suppress("UNCHECKED_CAST") override fun getO(map: Map<String, Any>): O {
		return (super.getO(map) as Good).apply {
			gType = C.gtom.selectSingle {
				gtCode = map[GT.gt_code.name].toString()
			}
		} as O
	}
}