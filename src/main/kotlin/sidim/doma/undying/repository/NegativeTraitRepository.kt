package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.records.ScholarNegativeTraitsRecord
import sidim.doma.undying.generated.tables.references.NEGATIVE_TRAITS
import sidim.doma.undying.generated.tables.references.SCHOLAR_NEGATIVE_TRAITS

@Repository
class NegativeTraitRepository(private val dslContext: DSLContext) {
    fun getActiveTraits(): List<NegativeTraits> {
        return dslContext.selectFrom(NEGATIVE_TRAITS)
            .where(NEGATIVE_TRAITS.IS_ACTIVE.eq(true))
            .fetchInto(NegativeTraits::class.java)
    }

    fun saveScholarNegativeTraits(scholarId: Long, traitIds: List<Int>) {
        val records = mutableListOf<ScholarNegativeTraitsRecord>()
        traitIds.forEach { id ->
            val r = dslContext.newRecord(SCHOLAR_NEGATIVE_TRAITS)
            r.scholarId = scholarId
            r.negativeTraitId = id
            records.add(r)
        }

        dslContext.batchInsert(records).execute()
    }

    fun createTrait(dto: NewTraitDto) {
        val r = dslContext.newRecord(NEGATIVE_TRAITS)
        r.name = dto.name
        r.description = dto.description
        r.isActive = true
        r.alchemyPenalty = dto.alchemyValue
        r.biologyPenalty = dto.biologyValue
        r.engineeringPenalty = dto.engineeringValue
        r.physicalHealthPenalty = dto.physicalHealthValue
        r.mentalHealthPenalty = dto.mentalHealthValue
        r.weight = dto.weight
        r.traitGroupId = dto.traitGroupId

        r.store()
    }

    fun findTraitsByScholarId(id: Long): List<NegativeTraits> {
        return dslContext.select(NEGATIVE_TRAITS)
            .from(NEGATIVE_TRAITS)
            .join(SCHOLAR_NEGATIVE_TRAITS)
            .on(NEGATIVE_TRAITS.NEGATIVE_TRAIT_ID.eq(SCHOLAR_NEGATIVE_TRAITS.NEGATIVE_TRAIT_ID))
            .where(
                SCHOLAR_NEGATIVE_TRAITS.SCHOLAR_ID.eq(id)
            ).fetchInto(NegativeTraits::class.java)
    }
}
