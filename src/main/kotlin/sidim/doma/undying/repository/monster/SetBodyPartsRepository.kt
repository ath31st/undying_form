package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record7
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.records.SetsBodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS

@Repository
class SetBodyPartsRepository(private val dslContext: DSLContext) {
    private val sbp = SETS_BODY_PARTS
    private val m = MONSTERS
    private val bp = BODY_PARTS
    private val sch = SCHOLARS

    fun existsSetBodyPartsWithId(id: Long): Boolean {
        return dslContext.selectCount()
            .from(sbp)
            .where(sbp.SET_BODY_PARTS_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }

    fun saveEmptySetBodyParts(): SetsBodyParts {
        val r = dslContext.newRecord(sbp)
        r.bodyPartsCount = 0
        r.bonusSet = 0
        r.setIsCompleted = false

        r.store()
        return r.into(SetsBodyParts::class.java)
    }

    fun findSetBodyPartsByMonsterId(monsterId: Long):
            Record7<SetsBodyPartsRecord, BodyPartsRecord, BodyPartsRecord,
                    BodyPartsRecord, BodyPartsRecord, BodyPartsRecord, BodyPartsRecord>? {
        return dslContext.select(
            sbp,
            bp.`as`("left_hand_bp"),
            bp.`as`("right_hand_bp"),
            bp.`as`("left_leg_bp"),
            bp.`as`("right_leg_bp"),
            bp.`as`("upper_body_bp"),
            bp.`as`("head_bp")
        )
            .from(sbp)
            .join(m).on(m.SET_BODY_PARTS_ID.eq(sbp.SET_BODY_PARTS_ID))
            .leftJoin(bp.`as`("left_hand_bp")).on(bp.`as`("left_hand_bp").BODY_PART_ID.eq(sbp.LEFT_HAND_SLOT))
            .leftJoin(bp.`as`("right_hand_bp")).on(bp.`as`("right_hand_bp").BODY_PART_ID.eq(sbp.RIGHT_HAND_SLOT))
            .leftJoin(bp.`as`("left_leg_bp")).on(bp.`as`("left_leg_bp").BODY_PART_ID.eq(sbp.LEFT_LEG_SLOT))
            .leftJoin(bp.`as`("right_leg_bp")).on(bp.`as`("right_leg_bp").BODY_PART_ID.eq(sbp.RIGHT_LEG_SLOT))
            .leftJoin(bp.`as`("upper_body_bp")).on(bp.`as`("upper_body_bp").BODY_PART_ID.eq(sbp.UPPER_BODY_SLOT))
            .leftJoin(bp.`as`("head_bp")).on(bp.`as`("head_bp").BODY_PART_ID.eq(sbp.HEAD_SLOT))
            .where(m.MONSTER_ID.eq(monsterId))
            .fetchOne()
    }

    fun findScholarIdBySetBodyPartsId(setBodyPartsId: Long): Long? {
        return dslContext.select(sch.SCHOLAR_ID)
            .from(sbp)
            .join(m).on(m.SET_BODY_PARTS_ID.eq(sbp.SET_BODY_PARTS_ID))
            .join(sch).on(sch.MONSTER_ID.eq(m.MONSTER_ID))
            .where(sbp.SET_BODY_PARTS_ID.eq(setBodyPartsId))
            .fetchOneInto(Long::class.java)
    }

    fun prepareIdsForDeletingAndUpdating(
        currentSlot: Long?,
        newSlotId: Long?,
        idsForDelBodyParts: MutableList<Long>,
        idsForUpdBodyParts: MutableList<Long>,
    ) {
        when {
            newSlotId == null && currentSlot != null -> {
                idsForDelBodyParts.add(currentSlot)
            }

            currentSlot == null && newSlotId != null -> {
                idsForUpdBodyParts.add(newSlotId)
            }

            currentSlot != null && newSlotId != currentSlot -> {
                idsForUpdBodyParts.add(newSlotId!!)
            }
        }
    }

    fun updateSlotsSetBodyParts(dto: SetBodyPartsUpdateDto): Pair<List<Long>, List<Long>> {
        val idsForDelBodyParts = mutableListOf<Long>()
        val idsForUpdBodyParts = mutableListOf<Long>()

        val currentRecord = dslContext.selectFrom(sbp)
            .where(sbp.SET_BODY_PARTS_ID.eq(dto.setBodyPartsId))
            .fetchOne()

        currentRecord?.let { r ->
            prepareIdsForDeletingAndUpdating(
                r.leftHandSlot,
                dto.leftHandIdForSlot,
                idsForDelBodyParts,
                idsForUpdBodyParts
            )
            r.leftHandSlot = dto.leftHandIdForSlot

            prepareIdsForDeletingAndUpdating(
                r.rightHandSlot,
                dto.rightHandIdForSlot,
                idsForDelBodyParts,
                idsForUpdBodyParts
            )
            r.rightHandSlot = dto.rightHandIdForSlot

            prepareIdsForDeletingAndUpdating(
                r.leftLegSlot,
                dto.leftLegIdForSlot,
                idsForDelBodyParts,
                idsForUpdBodyParts
            )
            r.leftLegSlot = dto.leftLegIdForSlot

            prepareIdsForDeletingAndUpdating(
                r.rightLegSlot,
                dto.rightLegIdForSlot,
                idsForDelBodyParts,
                idsForUpdBodyParts
            )
            r.rightLegSlot = dto.rightLegIdForSlot

            prepareIdsForDeletingAndUpdating(
                r.upperBodySlot,
                dto.upperBodyIdForSlot,
                idsForDelBodyParts,
                idsForUpdBodyParts
            )
            r.upperBodySlot = dto.upperBodyIdForSlot

            prepareIdsForDeletingAndUpdating(r.headSlot, dto.headIdForSlot, idsForDelBodyParts, idsForUpdBodyParts)
            r.headSlot = dto.headIdForSlot

            dslContext.update(sbp)
                .set(r)
                .where(sbp.SET_BODY_PARTS_ID.eq(dto.setBodyPartsId))
                .execute()
        }

        return Pair(idsForDelBodyParts, idsForUpdBodyParts)
    }
}
