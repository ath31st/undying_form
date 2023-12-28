package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.SPECIALIZATIONS

@Repository
class SpecializationRepository(private val dslContext: DSLContext) {
    fun getRandomSpecializationId(): Int {
        return dslContext.select(SPECIALIZATIONS.SPECIALIZATION_ID)
            .from(SPECIALIZATIONS)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java) ?: throw IllegalStateException("No specialization found")
    }

    fun isSpecializationExistById(id: Int): Boolean {
        return dslContext.selectCount()
            .from(SPECIALIZATIONS)
            .where(SPECIALIZATIONS.SPECIALIZATION_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }
}