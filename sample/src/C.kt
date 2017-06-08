import dto.*

object C {

	val ID = "id"
	val USER_BASE = ".e_user"
	val GOOD_BASE = ".e_good"
	val ORDER_BASE = ".e_order"
	val GOOD_TYPE_BASE = ".e_good_type"

	val uom: DTU<User> = DTU(User::class.java, USER_BASE)
	val gom: DTG<Good> = DTG(Good::class.java, GOOD_BASE)
	val oom: DTOR<Order> = DTOR(Order::class.java, ORDER_BASE)
	val gtom: DTGoodType<GType> = DTGoodType(GType::class.java, GOOD_TYPE_BASE)

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
