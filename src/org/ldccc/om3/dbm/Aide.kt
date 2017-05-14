package org.ldccc.om3.dbm

import org.ldccc.om3.dto.DTO
import java.lang.reflect.Field
import java.sql.ResultSet


object Aide {

	fun <O : DTO> findById(dtm: DTM<O>, id: Int) = DBManager.getSingleton().get(dtm, DTO.ID, id.toString())

	fun <O : DTO> findAll(dtm: DTM<O>) = DBManager.getSingleton().getAll(dtm)

	fun <O : DTO> addSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().add(dtm, o)

	fun <O : DTO> updateSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().update(dtm, o)

	fun <O : DTO> deleteSingle(dtm: DTM<O>, o: O) = DBManager.getSingleton().delete(dtm, o)

	fun field2column(field: Field) = camel2muji(field.name)

	fun camel2muji(arg: String) = arg.toCharArray()
			.map { if (it.isUpperCase().not()) it.toString() else "_" + it.toLowerCase().toString() }
			.reduce(Aide::concat)

	fun concat(s1: String, s2: String) = s1 + s2

}

