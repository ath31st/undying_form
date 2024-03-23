package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.HIDEOUTS
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS
import sidim.doma.undying.generated.tables.references.STORAGES

@Repository
class StorageRepository(private val dslContext: DSLContext) {
    private val st = STORAGES
    private val h = HIDEOUTS
    private val sc = SCHOLARS
    private val bp = BODY_PARTS
    private val sbp = SETS_BODY_PARTS
    private val m = MONSTERS

    fun saveNewStorage(capacity: Int): Storages {
        val r = dslContext.newRecord(st)
        r.capacity = capacity
        r.emptySlots = capacity

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

    fun getCountEmptySlotsByStorageId(storageId: Long): Int {
        return dslContext.select(st.EMPTY_SLOTS)
            .from(st)
            .where(st.STORAGE_ID.eq(storageId))
            .fetchOneInto(Int::class.java) ?: -1
    }

    fun updateCountEmptySlotsByStorageId(storageId: Long, changeCount: Int) {
        if (changeCount == 0) {
            return
        }

        val currentEmptySlots = getCountEmptySlotsByStorageId(storageId)

        dslContext.update(st)
            .set(st.EMPTY_SLOTS, currentEmptySlots + changeCount)
            .where(st.STORAGE_ID.eq(storageId))
            .execute()
    }

    fun existsBodyPartIdsInStorageByScholarId(dto: SetBodyPartsUpdateDto, scholarId: Long): Boolean {
        var bodyPartIds = setOf(
            dto.leftHandIdForSlot,
            dto.rightHandIdForSlot,
            dto.leftLegIdForSlot,
            dto.rightLegIdForSlot,
            dto.upperBodyIdForSlot,
            dto.headIdForSlot
        ).filterNotNull().toSet()

        val bodyPartsInSet = dslContext
            .select(bp.BODY_PART_ID)
            .from(bp)
            .join(sbp).on(sbp.SET_BODY_PARTS_ID.eq(bp.SET_BODY_PARTS_ID))
            .join(m).on(m.SET_BODY_PARTS_ID.eq(sbp.SET_BODY_PARTS_ID))
            .join(sc).on(sc.MONSTER_ID.eq(m.MONSTER_ID))
            .where(sc.SCHOLAR_ID.eq(scholarId))
            .fetchInto(Long::class.java)
            .toSet()

        bodyPartIds = bodyPartIds.minus(bodyPartsInSet)

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
