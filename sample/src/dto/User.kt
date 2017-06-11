package dto

import org.ldccc.om.dto.PO
import org.ldccc.om.core.DML
import java.sql.Date

data class User(
		override var id: Int? = null,
		var uName: String? = null,
		var uPassword: String? = null,
		var uEmail: String? = null,
		var uPhone: String? = null,
		var uActiveCode: String? = null,
		var uActiveStatus: Boolean? = null,
		var uProvince: String? = null,
		var uCity: String? = null,
		var uAddress: String? = null,
		var uRole: Boolean? = null,
		var uBirthday: Date? = null
) : PO(id)
