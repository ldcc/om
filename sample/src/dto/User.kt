package dto

import org.ldccc.om3.dbm.DTM
import org.ldccc.om3.dto.PO
import java.sql.Date

data class User(
		override var id: Int,
		var uName: String?,
		var uPassword: String?,
		var uEmail: String?,
		var uPhone: String?,
		var uActiveCode: Int?,
		var uActiveStatus: Boolean? = false,
		var uProvince: String? = "",
		var uCity: String? = "",
		var uAddress: String? = "",
		var uSex: Boolean? = false,
		var uRole: Boolean? = false,
		var uBirthday: Date? = Date(java.util.Date().time),
		var uCreateDate: Date? = Date(java.util.Date().time)
) : PO(id) {
	constructor() : this(0, null, null, null, null, null)
	constructor(name: String, password: String, email: String, phone: String, actCode: Int) : this(0, name, password, email, phone, actCode)
}

class DTUser<O : PO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base)




