package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.SelectField
import org.jooq.TableField
import org.jooq.TableLike
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
    private val hat = HAND_TEMPLATES
    private val lt = LEG_TEMPLATES
    private val ubt = UPPER_BODY_TEMPLATES
    private val het = HEAD_TEMPLATES
    private val gsc = GRAVEYARD_SOCIAL_CLASSES
    private val sc = SOCIAL_CLASSES

    private fun getRandomTemplateId(
        table: TableLike<*>,
        field: SelectField<*>,
        socialClassIdField: TableField<*, Int?>,
        graveyardId: Int,
        count: Int = 1
    ): Int? {
        return dslContext.select(field)
            .from(table)
            .join(sc).on(sc.SOCIAL_CLASS_ID.eq(socialClassIdField))
            .join(gsc).on(gsc.SOCIAL_CLASS_ID.eq(socialClassIdField))
            .where(gsc.GRAVEYARD_ID.eq(graveyardId))
            .orderBy(DSL.rand())
            .limit(count)
            .fetchOneInto(Int::class.java)
    }

    fun getRandomHandTemplateIdByGraveyardId(graveyardId: Int, count: Int = 1): Int? {
        return getRandomTemplateId(hat, hat.HAND_TEMPLATE_ID, hat.SOCIAL_CLASS_ID, graveyardId, count)
    }

    fun getRandomLegTemplateIdByGraveyardId(graveyardId: Int, count: Int = 1): Int? {
        return getRandomTemplateId(lt, lt.LEG_TEMPLATE_ID, lt.SOCIAL_CLASS_ID, graveyardId, count)
    }

    fun getRandomUpperBodyTemplateIdByGraveyardId(graveyardId: Int, count: Int = 1): Int? {
        return getRandomTemplateId(ubt, ubt.UPPER_BODY_TEMPLATE_ID, ubt.SOCIAL_CLASS_ID, graveyardId, count)
    }

    fun getRandomHeadTemplateIdByGraveyardId(graveyardId: Int, count: Int = 1): Int? {
        return getRandomTemplateId(het, het.HEAD_TEMPLATE_ID, het.SOCIAL_CLASS_ID, graveyardId, count)
    }
}