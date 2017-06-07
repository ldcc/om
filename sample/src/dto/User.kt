package dto

import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.OM
import java.sql.Date

data class User(override var id: Int, var uName: String?, var uPassword: String?, var uEmail: String?, var uPhone: String?, var uActiveCode: String?, var uActiveStatus: Boolean? = null, var uProvince: String? = null, var uCity: String? = null, var uAddress: String? = null, var uRole: Boolean? = null, var uBirthday: Date? = null) : PO(id) {
  constructor() : this(0, null, null, null, null, null)
  constructor(name: String, password: String, email: String, phone: String, actCode: String) : this(0, name, password, email, phone, actCode)
}

class DTU<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base)



