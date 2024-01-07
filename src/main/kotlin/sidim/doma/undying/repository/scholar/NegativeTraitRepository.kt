package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.records.ScholarNegativeTraitsRecord
import sidim.doma.undying.generated.tables.references.NEGATIVE_TRAITS
import sidim.doma.undying.generated.tables.references.SCHOLAR_NEGATIVE_TRAITS

@Repository
class NegativeTraitRepository(private val dslContext: DSLContext) {
    private val nt = NEGATIVE_TRAITS
    private val snt = SCHOLAR_NEGATIVE_TRAITS

    fun getActiveTraits(): List<NegativeTraits> {
        return dslContext.selectFrom(nt)
            .where(nt.IS_ACTIVE.eq(true))
            .fetchInto(NegativeTraits::class.java)
    }

    fun saveScholarNegativeTraits(scholarId: Long, traitIds: List<Int>) {
        val records = mutableListOf<ScholarNegativeTraitsRecord>()
        traitIds.forEach { id ->
            val r = dslContext.newRecord(snt)
            r.scholarId = scholarId
            r.negativeTraitId = id
            records.add(r)
        }

        dslContext.batchInsert(records).execute()
    }

    fun saveNewTrait(dto: NewTraitDto) {
        val r = dslContext.newRecord(nt)
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
        return dslContext.select(nt)
            .from(nt)
            .join(snt)
            .on(nt.NEGATIVE_TRAIT_ID.eq(snt.NEGATIVE_TRAIT_ID))
            .where(
                snt.SCHOLAR_ID.eq(id)
            ).fetchInto(NegativeTraits::class.java)
    }
}
