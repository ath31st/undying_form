package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.HANDS
import sidim.doma.undying.generated.tables.references.HAND_TEMPLATES
import sidim.doma.undying.generated.tables.references.HEADS
import sidim.doma.undying.generated.tables.references.HEAD_TEMPLATES
import sidim.doma.undying.generated.tables.references.LEGS
import sidim.doma.undying.generated.tables.references.LEG_TEMPLATES
import sidim.doma.undying.generated.tables.references.UPPER_BODIES
import sidim.doma.undying.generated.tables.references.UPPER_BODY_TEMPLATES

@Repository
class BodyPartsRepository(private val dslContext: DSLContext) {
    private val ha = HANDS
    private val th = HAND_TEMPLATES
    private val l = LEGS
    private val tl = LEG_TEMPLATES
    private val ub = UPPER_BODIES
    private val tub = UPPER_BODY_TEMPLATES
    private val he = HEADS
    private val the = HEAD_TEMPLATES
}