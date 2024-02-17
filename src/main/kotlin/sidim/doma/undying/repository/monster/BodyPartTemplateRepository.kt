package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.generated.tables.references.GRAVEYARD_SOCIAL_CLASSES
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES

@Repository
class BodyPartTemplateRepository(private val dslContext: DSLContext) {
    private val bpt = BODY_PART_TEMPLATES
    private val gsc = GRAVEYARD_SOCIAL_CLASSES
    private val sc = SOCIAL_CLASSES

    fun getRandomBodyPartTemplateIdByGraveyardIdAndBodyPartGroup(
        graveyardId: Int,
        bodyPartGroup: Int,
        count: Int = 1
    ): Int? {
        return dslContext.select(bpt.BODY_PART_TEMPLATE_ID)
            .from(bpt)
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .join(gsc).on(gsc.SOCIAL_CLASS_ID.eq(bpt.SOCIAL_CLASS_ID))
            .where(gsc.GRAVEYARD_ID.eq(graveyardId).and(bpt.BODY_PART_GROUP.eq(bodyPartGroup)))
            .orderBy(DSL.rand())
            .limit(count)
            .fetchOneInto(Int::class.java)
    }
}
