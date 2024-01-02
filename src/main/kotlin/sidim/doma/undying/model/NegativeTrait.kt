package sidim.doma.undying.model

import java.io.Serializable

data class NegativeTrait(
    var negativeTraitId: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var alchemyPenalty: Int? = null,
    var biologyPenalty: Int? = null,
    var engineeringPenalty: Int? = null,
    var physicalHealthPenalty: Int? = null,
    var mentalHealthPenalty: Int? = null,
    var weight: Int? = null,
    var traitGroupId: Int? = null
) : Serializable, Trait {
    override fun getId(): Int {
        return negativeTraitId ?: 0
    }

    override fun getGroupId(): Int {
        return traitGroupId ?: 0
    }
}