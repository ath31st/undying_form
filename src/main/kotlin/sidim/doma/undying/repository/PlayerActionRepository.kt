package sidim.doma.undying.repository

import java.time.LocalDateTime
import java.util.UUID
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.PlayerActions
import sidim.doma.undying.generated.tables.references.PLAYER_ACTIONS
import sidim.doma.undying.util.ActionTypes

@Repository
class PlayerActionRepository(private val dslContext: DSLContext) {
    private val pa = PLAYER_ACTIONS

    fun existsPlayerActionWithUuidAndScholarId(scholarId: Long, actionUuid: UUID): Boolean {
        return dslContext.selectCount()
            .from(pa)
            .where(pa.SCHOLAR_ID.eq(scholarId)).and(pa.PLAYER_ACTION_ID.eq(actionUuid))
            .fetchOneInto(Int::class.java) == 1
    }

    fun findPlayerActionByScholarIdAndUuid(scholarId: Long, actionUuid: UUID): PlayerActions? {
        return dslContext.select(pa)
            .from(pa)
            .where(pa.SCHOLAR_ID.eq(scholarId)).and(pa.PLAYER_ACTION_ID.eq(actionUuid))
            .fetchOneInto(PlayerActions::class.java)
    }

    fun savePlayerAction(scholarId: Long, actionUuid: UUID, actionType: ActionTypes, duration: Long): PlayerActions {
        val currentTime = LocalDateTime.now()

        val r = dslContext.newRecord(pa)
        r.playerActionId = actionUuid
        r.scholarId = scholarId
        r.actionType = actionType.ordinal
        r.createdAt = currentTime
        r.endAt = currentTime.plusMinutes(duration)

        r.store()
        return r.into(PlayerActions::class.java)
    }

    fun deletePlayerAction(scholarId: Long, actionUuid: UUID) {
        dslContext.deleteFrom(pa)
            .where(pa.SCHOLAR_ID.eq(scholarId))
            .and(pa.PLAYER_ACTION_ID.eq(actionUuid))
            .execute()
    }
}