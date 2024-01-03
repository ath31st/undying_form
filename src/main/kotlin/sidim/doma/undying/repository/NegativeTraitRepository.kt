package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.records.ScientistNegativeTraitsRecord
import sidim.doma.undying.generated.tables.references.NEGATIVE_TRAITS
import sidim.doma.undying.generated.tables.references.SCIENTIST_NEGATIVE_TRAITS

@Repository
class NegativeTraitRepository(private val dslContext: DSLContext) {
    fun getActiveTraits(): List<NegativeTraits> {
        return dslContext.selectFrom(NEGATIVE_TRAITS)
            .where(NEGATIVE_TRAITS.IS_ACTIVE.eq(true))
            .fetchInto(NegativeTraits::class.java)
    }

    fun saveScientistNegativeTraits(scientistId: Long, traitIds: List<Int>) {
        val records = mutableListOf<ScientistNegativeTraitsRecord>()
        traitIds.forEach { id ->
            val r = dslContext.newRecord(SCIENTIST_NEGATIVE_TRAITS)
            r.scientistId = scientistId
            r.negativeTraitId = id
            records.add(r)
        }

        dslContext.batchInsert(records).execute()
    }
}
