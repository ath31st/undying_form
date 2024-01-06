package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES

@Repository
class SocialClassesRepository(private val dslContext: DSLContext) {
    fun getRandomSocialClassId(): Int? {
        return dslContext.select(SOCIAL_CLASSES.SOCIAL_CLASS_ID)
            .from(SOCIAL_CLASSES)
            .where(SOCIAL_CLASSES.IS_ACTIVE.eq(true))
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}