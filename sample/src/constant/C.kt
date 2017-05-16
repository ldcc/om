package constant

import dto.DTUser
import dto.User
import org.ldccc.om3.dbm.DTM

object C {
	val USER_BASE = ".user"

	enum class U {
		user_name, user_password, auth, create_time, id
	}

	val dtu: DTM<User> = DTUser(User::class.java, USER_BASE)

	val uParams:Array<String> = U.values().map(U::name).toTypedArray()
}