package sidim.doma.undying.model

import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.util.BodyPartGroup

data class BodyPartTemplate(
    val bodyPartTemplateId: Int,
    val name: String,
    val description: String,
    val strength: Int,
    val agility: Int,
    val endurance: Int,
    val bodyPartGroup: BodyPartGroup,
    val socialClass: SocialClasses
)


