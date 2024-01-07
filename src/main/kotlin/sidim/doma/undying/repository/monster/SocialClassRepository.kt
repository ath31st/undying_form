package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES

@Repository
class SocialClassRepository(private val dslContext: DSLContext) {
    private val sc = SOCIAL_CLASSES

    fun createSocialClass(dto: NewSocialClassDto): SocialClasses {
        val r = dslContext.newRecord(sc)
        r.name = dto.name
        r.description = dto.description

        r.store()
        return r.into(SocialClasses::class.java)
    }

    fun getRandomSocialClassId(): Int? {
        return dslContext.select(sc.SOCIAL_CLASS_ID)
            .from(sc)
            .where(sc.IS_ACTIVE.eq(true))
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}