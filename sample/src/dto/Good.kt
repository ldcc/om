package dto

import constant.C
import constant.GT
import org.ldccc.om3.dto.PO
import org.ldccc.om3.om.Aide
import org.ldccc.om3.om.DML
import org.ldccc.om3.om.OM
import java.sql.Date

data class Good(override var id: Int, var gTitle: String?, var gBrand: String?, var gPrice: Double?, var gFavorablePrice: Double?, var gStorage: Int?, var gImage: String?, var gDescription: String?, var gGroundingDate: Date?, var gType: GType?) : PO(id) {
  constructor() : this(0, null, null, null, null, null, null, null, null, GType())
  
  override fun <O : PO> boxing(o: O): Any {
    return when (o) {
      is GType -> Aide.sign(o.gtCode as String)
      else -> "null"
    }
  }
}

class DTG<O : PO> constructor(tClass: Class<O>, base: String) : OM<O>(tClass, base) {
  init {
    columns[9] = "gt_code"
  }
  
  @Suppress("UNCHECKED_CAST") override fun getO(map: Map<String, Any>): O {
    return (super.getO(map) as Good).apply {
      this.gType = DML.selectSingle(C.dtgt) {
        it.gtCode = map[GT.gt_code.name].toString()
        it
      }
    } as O
  }
}