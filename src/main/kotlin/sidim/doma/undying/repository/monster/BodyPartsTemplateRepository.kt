package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.GRAVEYARD_SOCIAL_CLASSES
import sidim.doma.undying.generated.tables.references.HAND_TEMPLATES
import sidim.doma.undying.generated.tables.references.HEAD_TEMPLATES
import sidim.doma.undying.generated.tables.references.LEG_TEMPLATES
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES
import sidim.doma.undying.generated.tables.references.UPPER_BODY_TEMPLATES

@Repository
class BodyPartsTemplateRepository(private val dslContext: DSLContext) {
    private val th = HAND_TEMPLATES
    private val tl = LEG_TEMPLATES
    private val tub = UPPER_BODY_TEMPLATES
    private val the = HEAD_TEMPLATES
    private val gsc = GRAVEYARD_SOCIAL_CLASSES
    private val sc = SOCIAL_CLASSES

    fun getRandomHandTemplateByGraveyardId(graveyardId: Int, count: Int = 1): Int? {
        return dslContext.select(th.HAND_TEMPLATE_ID).from(th)
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(th.SOCIAL_CLASS_ID))
            .join(gsc).on(gsc.SOCIAL_CLASS_ID.eq(th.SOCIAL_CLASS_ID))
            .where(gsc.GRAVEYARD_ID.eq(graveyardId))
            .orderBy(DSL.rand())
            .limit(count)
            .fetchOneInto(Int::class.java)
    }
}