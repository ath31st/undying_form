package sidim.doma.undying.model

interface BodyPart {
    val id: Long
    val quality: Int
    val integrity: Int
    val storageId: Long?
    val setBodyPartsId: Long?
    val bodyPartTemplateId: Int
}

data class Hand(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    val side: String,
) : BodyPart

data class Leg(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    val side: String,
) : BodyPart

data class UpperBody(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
) : BodyPart

data class Head(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
) : BodyPart
