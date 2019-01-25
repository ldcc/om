import dto.User
import org.ldccc.om.core.DML

/**
 * Sample
 */

fun main(args: Array<String>) {
	getSingle()
	getAll()
	select1()
	select2()
	insert()
	update()
	delete()
}

private val USER_BASE = ".e_user"
private val uDml = DML(User::class.java, USER_BASE)

/**
 * find single
 */
private fun getSingle() {
	val user = uDml.selectSingle {
		id = 4
	}
	println(user)
}

/**
 * find All
 */
private fun getAll() {
	val users = uDml.selectAll().execute()
	users.forEach(::println)
}

/**
 * select
 */
private fun select1() {
	val users = uDml.select(USER_BASE) {
		"u_name" regexp "a" or "u_role" eq true orderBy "id desc" limit 3 offset 1
	}
	users.forEach(::println)
}

private fun select2() {
	val users = uDml
			.select { uName = "%a%"; uRole = true }
			.orderBy("id desc", "u_name asc").limit(3).offset(1).execute()
	users.forEach(::println)
}

/**
 * insert
 */
private fun insert() {
	uDml.insert {
		uName = "sbc"
		uPassword = "Aa0000"
	}
}

/**
 * update
 */
private fun update() {
	uDml.update {
		id = 6
		uProvince = "奥地利"
		uPassword = "Aa1111"
	}
}

/**
 * delete
 */
private fun delete() {
	uDml.delete {
		id = 8
	}
}
