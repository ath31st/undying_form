package sidim.doma.undying.model

import sidim.doma.undying.util.BodyPartGroup

data class BodyPart(
    val id: Long,
    val quality: Int,
    val integrity: Int,
    val side: String?,
    val storageId: Long?,
    val setBodyPartsId: Long?,
    val bodyPartTemplateId: Int,
    val bodyPartGroup: BodyPartGroup
)


