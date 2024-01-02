package sidim.doma.undying.model

import java.io.Serializable

data class PositiveTrait(
    var positiveTraitId: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var alchemyBonus: Int? = null,
    var biologyBonus: Int? = null,
    var engineeringBonus: Int? = null,
    var physicalHealthBonus: Int? = null,
    var mentalHealthBonus: Int? = null,
    var weight: Int? = null,
    var traitGroupId: Int? = null
) : Serializable, Trait
