package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.generated.tables.references.SOCIAL_CLASSES
import sidim.doma.undying.repository.CommonRepositoryMethods

@Repository
class SocialClassRepository(
    private val dslContext: DSLContext,
    private val commonRepositoryMethods: CommonRepositoryMethods,
) {
    private val sc = SOCIAL_CLASSES

    fun saveNewSocialClass(dto: NewSocialClassDto): SocialClasses {
        val r = dslContext.newRecord(sc)
        r.name = dto.name.trim()
        r.description = dto.description.trim()

        r.store()
        return r.into(SocialClasses::class.java)
    }

    fun isSocialClassExistByName(name: String): Boolean {
        return commonRepositoryMethods.isRecordExistByStringField(dslContext, sc, sc.NAME, name)
    }

    fun getSocialClasses(isActive: Boolean?): List<SocialClasses> {
        val classes: List<SocialClasses> = if (isActive != null)
            dslContext.select(sc)
                .from(sc)
                .where(sc.IS_ACTIVE.eq(isActive))
                .fetchInto(SocialClasses::class.java)
        else dslContext.select(sc)
            .from(sc)
            .fetchInto(SocialClasses::class.java)
        return classes
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