package sidim.doma.undying.dto.trait

import java.io.Serializable

data class NegativeTraitDto(
    var negativeTraitId: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var isActive: Boolean? = null,
    var alchemyPenalty: Int? = null,
    var biologyPenalty: Int? = null,
    var engineeringPenalty: Int? = null,
    var physicalHealthPenalty: Int? = null,
    var mentalHealthPenalty: Int? = null,
    var weight: Int? = null,
    var traitGroupId: Int? = null
) : Serializable


