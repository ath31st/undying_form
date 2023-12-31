package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.EDUCATION

@Repository
class EducationRepository(private val dslContext: DSLContext) {
    fun getRandomEducationId(): Int? {
        return dslContext.select(EDUCATION.EDUCATION_ID)
            .from(EDUCATION)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}
