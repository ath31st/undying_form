package sidim.doma.undying.model

interface BodyPart {
    val id: Long
    val quality: Int
    val integrity: Int
    val storageId: Int?
    val setBodyPartsId: Int?
}

data class Hand(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Int?,
    override val setBodyPartsId: Int?,
    val side: String,
) : BodyPart

data class Leg(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Int?,
    override val setBodyPartsId: Int?,
    val side: String,
) : BodyPart

data class UpperBody(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Int?,
    override val setBodyPartsId: Int?,
) : BodyPart

data class Head(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Int?,
    override val setBodyPartsId: Int?,
) : BodyPart
