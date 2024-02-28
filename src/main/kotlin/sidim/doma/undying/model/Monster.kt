package sidim.doma.undying.model

data class Monster(
    val monsterId: Long,
    val invisibility: Int,
    val strength: Int,
    val agility: Int,
    val endurance: Int,
    val stability: Int,
    val setBodyParts: SetBodyParts,
)