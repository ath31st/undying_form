package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Education
import sidim.doma.undying.generated.tables.references.EDUCATION
import sidim.doma.undying.generated.tables.references.SCHOLARS

@Repository
class EducationRepository(private val dslContext: DSLContext) {
    private val e = EDUCATION
    private val sc = SCHOLARS

    fun getRandomEducationId(): Int? {
        return dslContext.select(e.EDUCATION_ID)
            .from(e)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }

    fun findEducationByScholarId(scholarId: Long): Education? {
        return dslContext.select(e)
            .from(e)
            .join(sc).on(sc.EDUCATION_ID.eq(e.EDUCATION_ID))
            .where(sc.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Education::class.java)
    }
}
