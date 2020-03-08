package event

import dto.User
import org.ldcc.om.core.Engine

const val USER_TABLE = ".e_user"
val ueng = Engine(User::class.java, USER_TABLE)

/**
 * select all
 */
fun uselect1() {
    val users = ueng.selectall().execute()
    users.forEach(::println)
}

/**
 * select one
 */
fun uselect2() {
    val user = ueng.select1 { id = 4 }
    println(user)
}

/**
 * select
 */
fun uselect3() {
    val users = ueng.selectf {
        "u_name" regexp "a" or "u_role" eq true orderBy "id desc" limit 3 offset 1
    }
    users.forEach(::println)
}

fun uselect4() {
    val users = ueng
        .selectm { uName = "%a%"; uRole = true }
        .orderBy("id desc", "u_name asc").limit(3).offset(1).execute()
    users.forEach(::println)
}

/**
 * event.insert
 */
fun uinsert() {
    ueng.insert {
        uName = "ldc"
        uPassword = "Aa0000"
    }
}

/**
 * event.update
 */
fun uupdate() {
    ueng.update {
        id = 6
        uProvince = "奥地利"
        uPassword = "Aa1111"
    }
}

/**
 * event.delete
 */
fun udelete() {
    ueng.delete {
        id = 8
    }
}
