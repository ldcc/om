package constant

import dto.DTUser
import dto.User
import org.ldccc.om3.dbm.DTM

object C {
	val USER_BASE = ".users"

	enum class U {
		USER_NAME, USER_PASSWORD, AUTH, CREATE_TIME, ID
	}

	val dtu: DTM<User> = DTUser(User::class.java, USER_BASE)

	val uParams:Array<String> = U.values().map(U::name).toTypedArray()
}