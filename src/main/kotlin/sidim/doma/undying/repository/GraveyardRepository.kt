package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Graveyards
import sidim.doma.undying.generated.tables.references.GRAVEYARDS

@Repository
class GraveyardRepository(private val dslContext: DSLContext) {
    private val g = GRAVEYARDS

    fun findGraveyardById(graveyardId: Int): Graveyards? {
        return dslContext.select(g)
            .from(g)
            .where(g.GRAVEYARD_ID.eq(graveyardId))
            .fetchOneInto(Graveyards::class.java)
    }

    fun existsGraveyardWithId(id: Int): Boolean {
        return dslContext.selectCount()
            .from(g)
            .where(g.GRAVEYARD_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }
}
