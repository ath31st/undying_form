package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.HANDS
import sidim.doma.undying.generated.tables.references.HEADS
import sidim.doma.undying.generated.tables.references.LEGS
import sidim.doma.undying.generated.tables.references.UPPER_BODIES

@Repository
class BodyPartRepository(private val dslContext: DSLContext) {
    private val ha = HANDS
    private val l = LEGS
    private val ub = UPPER_BODIES
    private val he = HEADS
}