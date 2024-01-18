package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.records.BodyPartTemplatesRecord
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.records.SocialClassesRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.model.BodyPart

@Component
class BodyPartMapper(private val bodyPartTemplateMapper: BodyPartTemplateMapper) {
    private val bp = BODY_PARTS

    fun fromBodyPartRecordToModel(r1: BodyPartsRecord, r2: BodyPartTemplatesRecord, r3: SocialClassesRecord): BodyPart {
        return BodyPart(
            id = r1[bp.BODY_PART_ID] ?: 0,
            quality = r1[bp.QUALITY] ?: 0,
            integrity = r1[bp.INTEGRITY] ?: 0,
            side = r1[bp.SIDE],
            storageId = r1[bp.STORAGE_ID],
            setBodyPartsId = r1[bp.SET_BODY_PARTS_ID],
            bodyPartTemplate = bodyPartTemplateMapper.fromBodyPartTemplateRecordToModel(r2, r3)
        )
    }
}
