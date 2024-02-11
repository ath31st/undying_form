package sidim.doma.undying.repository

import java.util.UUID
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.PlayerActions
import sidim.doma.undying.generated.tables.references.PLAYER_ACTIONS
import sidim.doma.undying.util.ActionTypes

@Repository
class PlayerActionRepository(private val dslContext: DSLContext) {
    private val pa = PLAYER_ACTIONS

    fun savePlayerAction(scholarId: Long, uuid: UUID, actionType: ActionTypes): PlayerActions {
        val r = dslContext.newRecord(pa)
        r.playerActionId = uuid
        r.scholarId = scholarId
        r.actionType = actionType.ordinal

        r.store()
        return r.into(PlayerActions::class.java)
    }
}