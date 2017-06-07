package org.ldccc.om3.om

import org.ldccc.om3.dto.PO

object DML {
  
  infix fun String.eq(value: Any): String {
    return "$this = ${Aide.sign(value)}"
  }
  
  infix fun String.regexp(value: Any): String {
    return "$this REGEXP ${Aide.sign(value)}"
  }
  
  infix fun String.and(value: Any): String {
    return "$this && $value"
  }
  
  infix fun String.or(value: Any): String {
    return "$this || $value"
  }
  
  fun <O : PO> selectSingle(om: OM<O>, block: (O) -> O): O? {
    val a = Aide.selStatement(om.fields, om.columns, om.newO2(block))
    println("----------------------------------$a")
    return select(om, { "WHERE ${Aide.selStatement(om.fields, om.columns, om.newO2(block))}" }).getOrNull(0)
  }
  
  fun <O : PO> selectAll(om: OM<O>): List<O> {
    return select(om, { "" })
  }
  
  fun <O : PO> select(om: OM<O>, where: () -> String): List<O> {
    val sql = "SELECT * FROM ${om.base} ${where()};"
    println(sql)
    return om.select(sql)
  }
  
  fun <O : PO> insert(om: OM<O>, block: (O) -> O): Boolean {
    val sql = "INSERT INTO ${om.base} ${Aide.insStatement(om.fields, om.columns, om.newO2(block))}"
    println(sql)
    return om.add(sql)
  }
  
  fun <O : PO> update(om: OM<O>, block: (O) -> O): Boolean {
    val sql = "UPDATE $${om.base} SET ${Aide.updStatement(om.fields, om.columns, om.newO2(block))}"
    println(sql)
    return om.update(sql)
  }
  
  fun <O : PO> delete(om: OM<O>, block: (O) -> O): Boolean {
    val sql = "DELETE FROM ${om.base} WHERE ${Aide.delStatement(om.newO2(block))}"
    println(sql)
    return om.delete(sql)
  }
}


