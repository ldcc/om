package dto

import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.DTO
import java.util.*

data class User(var name: String?, var password: String?, var auth: Boolean?, var createTime: Date?, override var id: Int = 0) : DTO(id) {
	constructor() : this(null, null, null, null)
}

class DTUser<O : DTO> constructor(tClass: Class<O>, params: Array<String>, base: String) : DTM<O>(tClass, params, base)
