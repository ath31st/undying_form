package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS
import sidim.doma.undying.mapper.SetBodyPartsMapper
import sidim.doma.undying.model.SetBodyParts

@Repository
class SetBodyPartsRepository(private val dslContext: DSLContext, private val setBodyPartsMapper: SetBodyPartsMapper) {
    private val sbp = SETS_BODY_PARTS
    private val m = MONSTERS
    private val bp = BODY_PARTS

    fun saveEmptySetBodyParts(): SetsBodyParts {
        val r = dslContext.newRecord(sbp)
        r.bodyPartsCount = 0
        r.bonusSet = 0
        r.setIsCompleted = false

        r.store()
        return r.into(SetsBodyParts::class.java)
    }

    fun findSetBodyPartsByMonsterId(monsterId: Long): SetBodyParts? {
        val set = dslContext.select(
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

        return set?.let { setBodyPartsMapper.fromRecordToModel(it) }
    }
}
