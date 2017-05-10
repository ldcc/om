package constant

import dto.DTUser
import dto.User
import org.ldccc.om3.dbm.DTM
import java.lang.reflect.Field

object C {
	val USER_BASE = ".users"

	enum class U {
		USER_NAME, USER_PASSWORD, AUTH, CREATE_TIME, ID
	}

	val uFields: Array<Field> = User::class.java.declaredFields
	val uParams: Array<String> = U.values().map(U::name).toTypedArray()

	val dtu: DTM<User> = DTUser(User::class.java, uParams, USER_BASE)
}