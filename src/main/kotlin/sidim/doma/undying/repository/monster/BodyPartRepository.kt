package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.generated.tables.references.HIDEOUTS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.mapper.BodyPartMapper
import sidim.doma.undying.model.BodyPart

@Repository
class BodyPartRepository(private val dslContext: DSLContext, private val bodyPartMapper: BodyPartMapper) {
    private val bp = BODY_PARTS
    private val bpt = BODY_PART_TEMPLATES
    private val h = HIDEOUTS
    private val st = STORAGES
    private val sc = SOCIAL_CLASSES
    private val sch = SCHOLARS

    fun saveGeneratedBodyPart(dto: NewBodyPartDto): Long {
        val r = dslContext.newRecord(bp)
        r.scholarId = dto.scholarId
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
            .join(st).on(st.STORAGE_ID.eq(bp.STORAGE_ID))
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(st.STORAGE_ID.eq(storageId))
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

    fun findBodyPartsByScholarId(scholarId: Long): List<BodyPart> {
        return dslContext.select(bp, bpt, sc)
            .from(bp)
            .join(sch).on(sch.SCHOLAR_ID.eq(bp.SCHOLAR_ID))
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(
                sch.SCHOLAR_ID.eq(scholarId)
                    .and(bp.STORAGE_ID.isNull)
                    .and(bp.SET_BODY_PARTS_ID.isNull)
            )
            .fetch()
            .map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }

    fun existsBodyPartIdsForScholarId(bodyPartIds: Set<Long>, scholarId: Long): Boolean {
        val count = dslContext.selectCount()
            .from(bp)
            .where(
                bp.BODY_PART_ID.`in`(bodyPartIds).and(
                    bp.SCHOLAR_ID.eq(scholarId)
                        .and(bp.STORAGE_ID.isNull)
                        .and(bp.SET_BODY_PARTS_ID.isNull)
                )
            )
            .fetchOneInto(Int::class.java) ?: 0

        return count == bodyPartIds.size
    }

    fun updateBodyPartLocationToStorageWithStorageId(bodyPartIds: Set<Long>, storageId: Long) {
        val records = dslContext.selectFrom(bp)
            .where(bp.BODY_PART_ID.`in`(bodyPartIds))
            .fetch()

        records.map { r ->
            r.storageId = storageId
            r.scholarId = null
            r.setBodyPartsId = null
        }

        dslContext.batchStore(records).execute()
    }

    fun updateBodyPartLocationToStorageWithScholarId(bodyPartIds: Set<Long>, scholarId: Long) {
        val records = dslContext.selectFrom(bp)
            .where(bp.BODY_PART_ID.`in`(bodyPartIds))
            .fetch()

        val storageId = dslContext.select(st.STORAGE_ID)
            .from(st)
            .join(h).on(st.STORAGE_ID.eq(h.STORAGE_ID))
            .join(sch).on(h.HIDEOUT_ID.eq(sch.HIDEOUT_ID))
            .where(sch.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Long::class.java)

        records.map { r ->
            r.storageId = storageId
            r.scholarId = null
            r.setBodyPartsId = null
        }

        dslContext.batchStore(records).execute()
    }

    fun deleteExtraBodyPartsWithScholarId(scholarId: Long) {
        dslContext.deleteFrom(bp)
            .where(
                bp.SCHOLAR_ID.eq(scholarId)
                    .and(bp.STORAGE_ID.isNull)
            )
            .execute()
    }
}
