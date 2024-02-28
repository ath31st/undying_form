package sidim.doma.undying.model

data class SetBodyParts(
    var setBodyPartsId: Long,
    var bodyPartsCount: Int,
    var setIsCompleted: Boolean,
    var bonusSet: Int,
    var leftHandSlot: BodyPartShort? = null,
    var rightHandSlot: BodyPartShort? = null,
    var leftLegSlot: BodyPartShort? = null,
    var rightLegSlot: BodyPartShort? = null,
    var upperBodySlot: BodyPartShort? = null,
    var headSlot: BodyPartShort? = null
)