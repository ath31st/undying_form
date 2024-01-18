package sidim.doma.undying.model

data class BodyPart(
    val id: Long,
    val quality: Int,
    val integrity: Int,
    val side: String?,
    val storageId: Long?,
    val setBodyPartsId: Long?,
    val bodyPartTemplate: BodyPartTemplate,
)


