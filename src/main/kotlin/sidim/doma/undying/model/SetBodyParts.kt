package sidim.doma.undying.model

data class SetBodyParts(
    var setBodyPartsId: Long,
    var bodyPartsCount: Int,
    var setIsCompleted: Boolean,
    var bonusSet: Int,
    var leftHandSlot: BodyPart? = null,
    var rightHandSlot: BodyPart? = null,
    var leftLegSlot: BodyPart? = null,
    var rightLegSlot: BodyPart? = null,
    var upperBodySlot: BodyPart? = null,
    var headSlot: BodyPart? = null
)