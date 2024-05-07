package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.generated.tables.records.ScholarPositiveTraitsRecord
import sidim.doma.undying.generated.tables.references.POSITIVE_TRAITS
import sidim.doma.undying.generated.tables.references.SCHOLAR_POSITIVE_TRAITS
import sidim.doma.undying.repository.CommonRepositoryMethods

@Repository
class PositiveTraitRepository(
    private val dslContext: DSLContext,
    private val commonRepositoryMethods: CommonRepositoryMethods,
) {
    private val pt = POSITIVE_TRAITS
    private val spt = SCHOLAR_POSITIVE_TRAITS

    fun getActiveTraits(): List<PositiveTraits> {
        return dslContext.selectFrom(pt)
            .where(pt.IS_ACTIVE.eq(true))
            .fetchInto(PositiveTraits::class.java)
    }

    fun saveScholarPositiveTraits(scholarId: Long, traitIds: List<Int>) {
        val records = mutableListOf<ScholarPositiveTraitsRecord>()
        traitIds.forEach { id ->
            val r = dslContext.newRecord(spt)
            r.scholarId = scholarId
            r.positiveTraitId = id
            records.add(r)
        }

        dslContext.batchInsert(records).execute()
    }

    fun saveNewTrait(dto: NewTraitDto) {
        val r = dslContext.newRecord(pt)
        r.name = dto.name.trim()
        r.description = dto.description.trim()
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

    fun isPositiveTraitExistByName(name: String): Boolean {
        return commonRepositoryMethods.isRecordExistByStringField(dslContext, pt, pt.NAME, name.trim())
    }

    fun findTraitsByScholarId(id: Long): List<PositiveTraits> {
        return dslContext.select(pt)
            .from(pt)
            .join(spt)
            .on(pt.POSITIVE_TRAIT_ID.eq(spt.POSITIVE_TRAIT_ID))
            .where(
                spt.SCHOLAR_ID.eq(id)
            ).fetchInto(PositiveTraits::class.java)
    }
}
