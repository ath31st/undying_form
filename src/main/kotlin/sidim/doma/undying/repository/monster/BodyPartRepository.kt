package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record3
import org.jooq.Result
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.generated.tables.records.BodyPartTemplatesRecord
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.records.SocialClassesRecord
import sidim.doma.undying.generated.tables.references.*

@Repository
class BodyPartRepository(private val dslContext: DSLContext) {
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

    fun findBodyPartsByStorageId(storageId: Long):
            Result<Record3<BodyPartsRecord, BodyPartTemplatesRecord, SocialClassesRecord>> {
        return dslContext.select(bp, bpt, sc)
            .from(bp)
            .join(st).on(st.STORAGE_ID.eq(bp.STORAGE_ID))
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(st.STORAGE_ID.eq(storageId))
            .fetch()
    }

    fun findBodyPartsByIds(listIds: List<Long>):
            Result<Record3<BodyPartsRecord, BodyPartTemplatesRecord, SocialClassesRecord>> {
        return dslContext.select(bp, bpt, sc)
            .from(bp)
            .join(bpt).on(bpt.BODY_PART_TEMPLATE_ID.eq(bp.BODY_PART_TEMPLATE_ID))
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(bp.BODY_PART_ID.`in`(listIds))
            .fetch()
    }

    fun findBodyPartsByScholarId(scholarId: Long):
            Result<Record3<BodyPartsRecord, BodyPartTemplatesRecord, SocialClassesRecord>> {
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

    fun updateBodyPartLocationToSetWithSetId(idsForTransferring: List<Long>, setBodyPartsId: Long) {
        val records = dslContext.selectFrom(bp)
            .where(bp.BODY_PART_ID.`in`(idsForTransferring))
            .fetch()

        records.map { r ->
            r.storageId = null
            r.scholarId = null
            r.setBodyPartsId = setBodyPartsId
        }

        dslContext.batchStore(records).execute()
    }

    fun deleteExtraBodyPartsWithScholarId(scholarId: Long) {
        dslContext.deleteFrom(bp)
            .where(
                bp.SCHOLAR_ID.eq(scholarId)
                    .and(bp.STORAGE_ID.isNull)
                    .and(bp.SET_BODY_PARTS_ID.isNull)
            )
            .execute()
    }

    fun deleteExtraBodyPartsAfterUpdateSet(idsForDeleting: List<Long>) {
        dslContext.deleteFrom(bp)
            .where(bp.BODY_PART_ID.`in`(idsForDeleting))
            .execute()
    }

    fun deleteBodyPartsFromStorage(storageId: Long, bodyPartIds: List<Long>): Int {
        return dslContext.deleteFrom(bp)
            .where(bp.STORAGE_ID.eq(storageId).and(bp.BODY_PART_ID.`in`(bodyPartIds)))
            .execute()
    }
}
