package org.ldccc.om3.om

import org.ldccc.om3.dto.PO

object DML {

//	inline fun <O : PO> selectSingle(om: OM<O>, block: O.() -> Unit): O? {
//		return select(om, { "WHERE ${Aide.selStatement(om.fields, om.columns, om.new1(block))}" }).getOrNull(0)
//	}
//
//	fun <O : PO> selectAll(om: OM<O>): List<O> {
//		return select(om, { "" })
//	}
//
//	inline fun <O : PO> select(om: OM<O>, where: () -> String): List<O> {
//		val sql = "SELECT * FROM ${om.base} ${where()};"
//		println(sql)
//		return om.select(sql)
//	}
//
//	inline fun <O : PO> insert(om: OM<O>, block: O.() -> Unit): Boolean {
//		val sql = "INSERT INTO ${om.base} ${Aide.insStatement(om.fields, om.columns, om.new1(block))}"
//		println(sql)
//		return om.add(sql)
//	}
//
//	inline fun <O : PO> update(om: OM<O>, block: O.() -> Unit): Boolean {
//		val sql = "UPDATE $${om.base} SET ${Aide.updStatement(om.fields, om.columns, om.new1(block))}"
//		println(sql)
//		return om.update(sql)
//	}
//
//	inline fun <O : PO> delete(om: OM<O>, block: O.() -> Unit): Boolean {
//		val sql = "DELETE FROM ${om.base} WHERE ${Aide.delStatement(om.new1(block))}"
//		println(sql)
//		return om.delete(sql)
//	}
}