package dto

import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.PO
import java.util.*

data class User(var userName: String?, var userPassword: String?, var auth: Boolean?, var createTime: Date?, override var id: Int = 0) : PO(id) {
	constructor() : this(null, null, null, null)
}

class DTUser<O : PO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base)
