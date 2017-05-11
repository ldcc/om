package org.ldccc.om3.dto

import org.ldccc.om3.dbm.DBManager
import org.ldccc.om3.dbm.DTM

object Operator {

	fun <O : DTO> selectById(dtm: DTM<O>, id: Int) = DBManager.getSingleton().get(dtm, DTO.Companion.ID, id.toString())

	fun <O : DTO> selectAll(dtm: DTM<O>) = DBManager.getSingleton().getAll(dtm)

	fun <O : DTO> addSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().add(dtm, o)

	fun <O : DTO> updateSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().update(dtm, o)

	fun <O : DTO> deleteSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().delete(dtm, o)

}