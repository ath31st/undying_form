package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.SPECIALIZATIONS

@Repository
class SpecializationRepository(private val dslContext: DSLContext) {
    private val s = SPECIALIZATIONS

    fun getRandomSpecializationId(): Int? {
        return dslContext.select(s.SPECIALIZATION_ID)
            .from(s)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }

    fun isSpecializationExistById(id: Int): Boolean {
        return dslContext.selectCount()
            .from(s)
            .where(s.SPECIALIZATION_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }
}
