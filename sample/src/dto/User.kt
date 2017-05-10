package dto

import constant.C
import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.DTO
import java.util.*

data class User(var name: String?, var password: String?, var auth: Boolean?, var createTime: Date?, var id: Int = 0) : DTO() {
	constructor() : this(null, null, null, null)
	constructor(map: Map<String, Any>) : this() {
		C.uFields.indices.forEach { C.uFields[it].set(this, map[C.uParams[it]]) }
	}

	init {
		C.uFields.indices.forEach { map.put(C.uParams[it], C.uFields[it].get(this)) }
	}
}

class DTUser<O : DTO> constructor(tClass: Class<O>, params: Array<String>, base: String) : DTM<O>(tClass, params, base)
