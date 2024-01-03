package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.generated.tables.records.ScientistPositiveTraitsRecord
import sidim.doma.undying.generated.tables.references.POSITIVE_TRAITS
import sidim.doma.undying.generated.tables.references.SCIENTIST_POSITIVE_TRAITS

@Repository
class PositiveTraitRepository(private val dslContext: DSLContext) {
    fun getActiveTraits(): List<PositiveTraits> {
        return dslContext.selectFrom(POSITIVE_TRAITS)
            .where(POSITIVE_TRAITS.IS_ACTIVE.eq(true))
            .fetchInto(PositiveTraits::class.java)
    }

    fun saveScientistPositiveTraits(scientistId: Long, traitIds: List<Int>) {
        val records = mutableListOf<ScientistPositiveTraitsRecord>()
        traitIds.forEach { id ->
            val r = dslContext.newRecord(SCIENTIST_POSITIVE_TRAITS)
            r.scientistId = scientistId
            r.positiveTraitId = id
            records.add(r)
        }

        dslContext.batchInsert(records).execute()
    }

    fun createTrait(dto: NewTraitDto) {
        val r = dslContext.newRecord(POSITIVE_TRAITS)
        r.name = dto.name
        r.description = dto.description
        r.isActive = true
        r.alchemyBonus = dto.alchemyValue
        r.biologyBonus = dto.biologyValue
        r.engineeringBonus = dto.engineeringValue
        r.physicalHealthBonus = dto.physicalHealthValue
        r.mentalHealthBonus = dto.mentalHealthValue
        r.weight = dto.weight
        r.traitGroupId = dto.traitGroupId

        r.store()
    }
}
