package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.generated.tables.records.BodyPartTemplatesRecord
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.util.BodyPartGroup

@Repository
class BodyPartRepository(private val dslContext: DSLContext) {
    private val bp = BODY_PARTS
    private val bpt = BODY_PART_TEMPLATES
    private val s = STORAGES

    fun saveGeneratedBodyPartInStorage(dto: NewBodyPartDto): BodyPart {
        val r = dslContext.newRecord(bp)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.side = dto.side
        r.bodyPartTemplateId = dto.templateId
        r.storageId = dto.storageId

        r.store()
        return r.into(
            BodyPart(
                id = r.bodyPartId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.bodyPartTemplateId ?: 0,
                bodyPartGroup = BodyPartGroup.HANDS,
                side = r.side
            )
        )
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        return dslContext.select(bp, bpt)
            .from(bp)
            .join(s)
            .on(s.STORAGE_ID.eq(bp.STORAGE_ID))
            .join(bpt)
            .on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map { mapBodyPartFromRecord(it.value1(), it.value2()) }
    }

    private fun mapBodyPartFromRecord(r1: BodyPartsRecord, r2: BodyPartTemplatesRecord): BodyPart {
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
