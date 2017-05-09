package dto

import org.ldccc.om2.dbm.DTM
import org.ldccc.om2.dto.DTO
import java.lang.reflect.Field
import java.util.*

data class User1(var name: String?, var password: String?, var auth: Boolean?, var createTime: Date?, var id: Int = 0) : DTO() {
	constructor() : this(null, null, null, null)
	constructor(map: Map<String, Any>) : this() {
		this.map = map
		a.fields.indices.forEach { a.fields[it].set(this, map[a.attrs[it]]) }
	}

}

private class DTUser<O : DTO> constructor(tClass: Class<O>, base: String) : DTM<O>(tClass, base)

object a {
	val USER_BASE = ".users"
	val USER_NAME = "USER_NAME"
	val USER_PASS = "USER_PASSWORD"
	val USER_AUTH = "AUTH"
	val USER_CREATE_TIME = "CREATE_TIME"
	val ID = "ID"

	val DTM: DTM<User1> = DTUser(User1::class.java, USER_BASE)

	val fields: Array<out Field> = User1::class.java.declaredFields
	val attrs: List<String> = listOf(USER_NAME, USER_PASS, USER_AUTH, USER_CREATE_TIME, ID)

}