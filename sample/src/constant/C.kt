package constant

import dto.DTG
import dto.DTU
import dto.Good
import dto.User
import dto.DTGoodType
import dto.DTOR
import dto.GType
import dto.Order

object C {

	val ID = "id"
	val USER_BASE = ".e_user"
	val GOOD_BASE = ".e_good"
	val ORDER_BASE = ".e_order"
	val OG_ITEM_BASE = ".e_og_item"
	val GOOD_TYPE_BASE = ".e_good_type"

	val dtu: DTU<User> = DTU(User::class.java, USER_BASE)
	val dtg: DTG<Good> = DTG(Good::class.java, GOOD_BASE)
	val dtor: DTOR<Order> = DTOR(Order::class.java, ORDER_BASE)
	//	val dtogi: DTOGI<OGItem> = DTOGI(OGItem::class.java, OG_ITEM_BASE)
	val DTGT: DTGoodType<GType> = DTGoodType(GType::class.java, GOOD_TYPE_BASE)

}

enum class U {
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


enum class GT {
	gt_code,
	gt_name,
	gt_remark,
}
