package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.EDUCATION

@Repository
class EducationRepository(private val dslContext: DSLContext) {
    private val e = EDUCATION

    fun getRandomEducationId(): Int? {
        return dslContext.select(e.EDUCATION_ID)
            .from(e)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}
