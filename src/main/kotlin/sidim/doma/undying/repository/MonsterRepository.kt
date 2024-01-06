package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.MONSTERS

@Repository
class MonsterRepository(private val dslContext: DSLContext) {
    private val m = MONSTERS
}