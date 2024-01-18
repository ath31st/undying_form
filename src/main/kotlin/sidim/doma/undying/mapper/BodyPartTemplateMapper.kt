package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.generated.tables.records.BodyPartTemplatesRecord
import sidim.doma.undying.generated.tables.records.SocialClassesRecord
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.model.BodyPartTemplate
import sidim.doma.undying.util.BodyPartGroup

@Component
class BodyPartTemplateMapper {
    private val bpt = BODY_PART_TEMPLATES

    fun fromBodyPartTemplateRecordToModel(r1: BodyPartTemplatesRecord, r2: SocialClassesRecord): BodyPartTemplate {
        return BodyPartTemplate(
            bodyPartTemplateId = r1[bpt.BODY_PART_TEMPLATE_ID] ?: 0,
            name = r1[bpt.NAME] ?: "",
            description = r1[bpt.DESCRIPTION] ?: "",
            strength = r1[bpt.STRENGTH] ?: 0,
            agility = r1[bpt.AGILITY] ?: 0,
            endurance = r1[bpt.ENDURANCE] ?: 0,
            bodyPartGroup = BodyPartGroup.entries[r1[bpt.BODY_PART_GROUP] ?: -1],
            socialClass = r2.into(SocialClasses::class.java)
        )
    }
}
