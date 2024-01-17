package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.records.BodyPartTemplatesRecord
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.util.BodyPartGroup

@Component
class BodyPartMapper {
    private val bp = BODY_PARTS
    private val bpt = BODY_PART_TEMPLATES

    fun fromBodyPartRecordToModel(r1: BodyPartsRecord, r2: BodyPartTemplatesRecord): BodyPart {
        return BodyPart(
            id = r1[bp.BODY_PART_ID] ?: 0,
            quality = r1[bp.QUALITY] ?: 0,
            integrity = r1[bp.INTEGRITY] ?: 0,
            storageId = r1[bp.STORAGE_ID],
            setBodyPartsId = r1[bp.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r1[bp.BODY_PART_TEMPLATE_ID] ?: 0,
            side = r1[bp.SIDE],
            bodyPartGroup = BodyPartGroup.entries[r2[bpt.BODY_PART_GROUP]
                ?: -1], // todo rework this to object field BodyPartTemplate
        )
    }
}
