package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.mapper.BodyPartMapper
import sidim.doma.undying.model.BodyPart

@Repository
class BodyPartRepository(private val dslContext: DSLContext, private val bodyPartMapper: BodyPartMapper) {
    private val bp = BODY_PARTS
    private val bpt = BODY_PART_TEMPLATES
    private val s = STORAGES
    private val sc = SOCIAL_CLASSES

    fun saveGeneratedBodyPart(dto: NewBodyPartDto): Long {
        val r = dslContext.newRecord(bp)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.side = dto.side
        r.bodyPartTemplateId = dto.templateId

        r.store()
        return r.bodyPartId ?: -1
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        return dslContext.select(bp, bpt, sc)
            .from(bp)
            .join(s).on(s.STORAGE_ID.eq(bp.STORAGE_ID))
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }

    fun findBodyPartsByIds(listIds: List<Long>): List<BodyPart> {
        return dslContext.select(bp, bpt, sc)
            .from(bp)
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(bp.BODY_PART_ID.`in`(listIds))
            .fetch()
            .map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }
}
