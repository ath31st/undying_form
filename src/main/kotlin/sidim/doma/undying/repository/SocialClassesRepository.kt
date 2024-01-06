package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES

@Repository
class SocialClassesRepository(private val dslContext: DSLContext) {
    private val sc = SOCIAL_CLASSES
    
    fun getRandomSocialClassId(): Int? {
        return dslContext.select(sc.SOCIAL_CLASS_ID)
            .from(sc)
            .where(sc.IS_ACTIVE.eq(true))
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}