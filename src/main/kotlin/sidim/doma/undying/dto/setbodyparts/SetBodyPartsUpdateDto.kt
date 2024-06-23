package sidim.doma.undying.dto.setbodyparts

data class SetBodyPartsUpdateDto(
    val setBodyPartsId: Long,
    val leftHandIdForSlot: Long?,
    val rightHandIdForSlot: Long?,
    val leftLegIdForSlot: Long?,
    val rightLegIdForSlot: Long?,
    val upperBodyIdForSlot: Long?,
    val headIdForSlot: Long?,
)
