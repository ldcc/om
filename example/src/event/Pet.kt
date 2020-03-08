package event

import dto.Pet
import org.ldcc.om.core.Engine
import java.sql.Date

const val PET_TABLE = ".pet"
val peng = Engine(Pet::class.java, PET_TABLE)

/**
 * select all
 */
fun pselect1() {
    peng.selectall().execute()
        .forEach(::println)
}

/**
 * select one
 */
fun pselect2() {
    peng.select1 { id = 1 }
        .run(::println)
}

/**
 * select
 */
fun pselect3() {
    peng.selectf { "name" regexp "i" or "sex" eq "m" orderBy "birth asc" limit 3 offset 0 }
        .forEach(::println)
}

fun pselect4() {
    peng.selectm { name = "%i%"; sex = "m" }
        .orderBy("id asc", "birth asc")
        .limit(3)
        .offset(0)
        .execute()
        .forEach(::println)
}

/**
 * event.insert
 */
fun pinsert() {
    peng.insert {
        name = "Frey"
        owner = "Ldc"
        species = "giraffe"
        birth = Date.valueOf("1997-05-06")
    }
}

/**
 * event.update
 */
fun pupdate() {
    peng.update {
        id = 3
        name = "Gray"
    }
}

/**
 * event.delete
 */
fun pdelete() {
    peng.delete {
        id = 3
    }
}
