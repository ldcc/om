package constant

import dto.DTU
import dto.User
import org.ldcc.martin.dto.DTG
import org.ldcc.martin.dto.DTGoodType
import org.ldcc.martin.dto.Good
import org.ldcc.martin.dto.GoodType

object C {
	val USER_BASE = ".e_user"
	val GOOD_BASE = ".e_good"
	val GOOD_TYPE_BASE = ".e_good_type"

	val dtu: DTU<User> = DTU(User::class.java, USER_BASE)
	val dtg: DTG<Good> = DTG(Good::class.java, GOOD_BASE)
	val dtgt: DTGoodType<GoodType> = DTGoodType(GoodType::class.java, GOOD_TYPE_BASE)

}

enum class U {
	id,
	u_name,
	u_password,
	u_email,
	u_phone,
	u_active_code,
	u_active_status,
	u_province,
	u_city,
	u_address,
	u_sex,
	u_role,
	u_birthday,
	u_create_date,
}

enum class G{}

enum class GT{
	gt_code,
	gt_name,
	gt_remark,
}
