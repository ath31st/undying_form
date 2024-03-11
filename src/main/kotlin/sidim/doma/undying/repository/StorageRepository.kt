package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.HIDEOUTS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.util.constant.StorageConstants.CAPACITY

@Repository
class StorageRepository(private val dslContext: DSLContext) {
    private val st = STORAGES
    private val h = HIDEOUTS
    private val sc = SCHOLARS
    private val bp = BODY_PARTS

    fun saveStorage(): Storages {
        val r = dslContext.newRecord(st)
        r.capacity = CAPACITY

        r.store()
        return r.into(Storages::class.java)
    }

    fun findStorageByScholarId(scholarId: Long): Storages? {
        return dslContext.select(st)
            .from(st)
            .join(h).on(st.STORAGE_ID.eq(h.STORAGE_ID))
            .join(sc).on(h.HIDEOUT_ID.eq(sc.HIDEOUT_ID))
            .where(sc.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Storages::class.java)
    }

    fun existsBodyPartIdsInStorageByScholarId(dto: SetBodyPartsUpdateDto, scholarId: Long): Boolean {
        val bodyPartIds = setOf(
            dto.leftHandIdForSlot,
            dto.rightHandIdForSlot,
            dto.leftLegIdForSlot,
            dto.rightLegIdForSlot,
            dto.upperBodyIdForSlot,
            dto.headIdForSlot
        ).filterNotNull().toSet()

        val count = dslContext.selectCount()
            .from(bp)
            .join(h).on(h.HIDEOUT_ID.eq(scholarId))
            .where(
                bp.BODY_PART_ID.`in`(bodyPartIds).and(
                    bp.STORAGE_ID.eq(h.STORAGE_ID)
                        .and(bp.SCHOLAR_ID.isNull)
                        .and(bp.SET_BODY_PARTS_ID.isNull)
                )
            )
            .fetchOneInto(Int::class.java) ?: 0

        return count >= bodyPartIds.size
    }
}
