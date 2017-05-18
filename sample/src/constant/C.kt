package constant

import dto.DTUser
import dto.User
import org.ldccc.om3.dbm.DTM

object C {
	val USER_BASE = ".e_user"

	val dtu: DTM<User> = DTUser(User::class.java, USER_BASE)

}