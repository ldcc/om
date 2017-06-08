package dto

import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.OM

data class GType(var gtCode: String? = null, var gtName: String? = null, var gtRemark: String? = null) : PO()

class DTGoodType<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base)