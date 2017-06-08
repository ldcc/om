package dto

import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.DML
import java.sql.Date

data class User(override var id: Int? = null, var uName: String? = null, var uPassword: String? = null, var uEmail: String? = null, var uPhone: String? = null, var uActiveCode: String? = null, var uActiveStatus: Boolean? = null, var uProvince: String? = null, var uCity: String? = null, var uAddress: String? = null, var uRole: Boolean? = null, var uBirthday: Date? = null) : PO(id) {
  constructor(name: String, password: String, email: String, phone: String, actCode: String) : this(0, name, password, email, phone, actCode)
}

class DTU<O : PO> constructor(tClass: Class<O>, base: String) : DML<O>(tClass, base)
