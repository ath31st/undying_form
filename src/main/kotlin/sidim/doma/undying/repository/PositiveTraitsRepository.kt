package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.generated.tables.references.POSITIVE_TRAITS

@Repository
class PositiveTraitsRepository(private val dslContext: DSLContext) {
    fun getAllTraits(): List<PositiveTraits> {
        return dslContext.selectFrom(POSITIVE_TRAITS)
            .fetchInto(PositiveTraits::class.java)
    }
}