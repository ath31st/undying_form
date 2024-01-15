package sidim.doma.undying.model

import sidim.doma.undying.util.BodyPartsGroup

interface BodyPart {
    val id: Long
    val quality: Int
    val integrity: Int
    val storageId: Long?
    val setBodyPartsId: Long?
    val bodyPartTemplateId: Int
    val bodyPartsGroup: BodyPartsGroup
}

data class Hand(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    override val bodyPartsGroup: BodyPartsGroup,
    val side: String,
) : BodyPart

data class Leg(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    override val bodyPartsGroup: BodyPartsGroup,
    val side: String,
) : BodyPart

data class UpperBody(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    override val bodyPartsGroup: BodyPartsGroup,
) : BodyPart

data class Head(
    override val id: Long,
    override val quality: Int,
    override val integrity: Int,
    override val storageId: Long?,
    override val setBodyPartsId: Long?,
    override val bodyPartTemplateId: Int,
    override val bodyPartsGroup: BodyPartsGroup,
) : BodyPart
