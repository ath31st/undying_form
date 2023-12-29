package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.references.NEGATIVE_TRAITS

@Repository
class NegativeTraitRepository(private val dslContext: DSLContext) {
    fun getAllTraits(): List<NegativeTraits> {
        return dslContext.selectFrom(NEGATIVE_TRAITS)
            .fetchInto(NegativeTraits::class.java)
    }
}