package dto

import org.ldcc.om.dto.PO
import java.sql.Date

data class Pet(
    override var id: Int? = null,
    var name: String? = null,
    var owner: String? = null,
    var species: String? = null,
    var sex: String? = null,
    var birth: Date? = null,
    var death: Date? = null
) : PO(id)