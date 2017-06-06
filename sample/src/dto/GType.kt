package dto

import org.ldccc.om3.dbm.OM
import org.ldccc.om3.dto.PO

data class GType(var gtCode: String?, var gtName: String?, var gtRemark: String?) : PO() {
	constructor() : this(null,null,null)
}

class DTGoodType<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base)