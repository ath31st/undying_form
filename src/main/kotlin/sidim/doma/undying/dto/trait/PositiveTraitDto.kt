package sidim.doma.undying.dto.trait

class PositiveTraitDto(
    val traitId: Int,
    val name: String,
    val description: String,
    val isActive: Boolean,
    val alchemyBonus: Int,
    val biologyBonus: Int,
    val engineeringBonus: Int,
    val physicalHealthBonus: Int,
    val mentalHealthBonus: Int,
    val weight: Int,
    val traitGroupId: Int,
)