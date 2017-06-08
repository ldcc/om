package dto

import C
import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.Aide
import org.ldccc.om3.om.OM
import java.sql.Timestamp

data class Order(override var id: Int, var oCode: String? = null, var oCreate: Timestamp? = null, var oSend: Timestamp? = null, var oStatus: Int? = null, var oAmount: Double? = null, var oPay: Boolean? = null, var user: User? = User()) : PO(id) {
	constructor() : this(0)
	constructor(code: String?, create: Timestamp?, amount: Double?, user: User?) : this(0, code, create, null, 0, amount, false, user)

	override fun <O : PO> boxing(o: O): Any {
		return when (o) {
			is User -> Aide.sign(o.id)
			else -> "null"
		}
	}
}

class DTOR<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base) {
	@Suppress("UNCHECKED_CAST") override fun getO(map: Map<String, Any>): O {
		return (super.getO(map) as Order).apply {
			user = C.uom.selectSingle {
				id = map[C.ID].toString().toInt()
			}
		} as O
	}

	init {
		columns[7] = "u_id"
	}
}
