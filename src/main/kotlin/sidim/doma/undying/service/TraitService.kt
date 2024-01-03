package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.dto.trait.ScientistTraitsDto
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.mapper.TraitMapper
import sidim.doma.undying.repository.NegativeTraitRepository
import sidim.doma.undying.repository.PositiveTraitRepository
import sidim.doma.undying.util.constant.TraitConstants.COUNT_TRAITS
import sidim.doma.undying.util.constant.TraitConstants.TRAITS_WEIGHT_SUM

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository,
    private val traitMapper: TraitMapper
) {
    fun generateAndSaveRandomSetTraits(scientistId: Long) {
        val traits = collectScientistRandomTraits()
        val posTraits = traits.first
        val negTraits = traits.second

        positiveTraitRepository.saveScientistPositiveTraits(
            scientistId,
            posTraits.map { it.positiveTraitId ?: 0 }.toList()
        )
        negativeTraitRepository.saveScientistNegativeTraits(
            scientistId,
            negTraits.map { it.negativeTraitId ?: 0 }.toList()
        )
    }

    fun collectScientistRandomTraits(): Pair<List<PositiveTraits>, List<NegativeTraits>> {
        var weight = TRAITS_WEIGHT_SUM
        val posTraits = mutableSetOf<PositiveTraits>()
        val negTraits = mutableSetOf<NegativeTraits>()
        val traitGroupIds = mutableSetOf<Int>()

        while ((posTraits.size + negTraits.size) < COUNT_TRAITS && weight > 0) {
            val posTrait = getActivePositiveTraits()
                .shuffled()
                .firstOrNull {
                    weight - it.weight!! >= 1 && traitGroupIds.contains(it.traitGroupId).not()
                }
            posTrait?.let {
                weight -= it.weight!!
                traitGroupIds.add(it.traitGroupId!!)
                posTraits.add(it)
            }

            if ((posTraits.size + negTraits.size) < COUNT_TRAITS && weight > 0) {
                val negTrait = getActiveNegativeTraits()
                    .shuffled()
                    .firstOrNull {
                        weight - it.weight!! >= 0 && traitGroupIds.contains(it.traitGroupId).not()
                    }
                negTrait?.let {
                    weight -= it.weight!!
                    traitGroupIds.add(it.traitGroupId!!)
                    negTraits.add(it)
                }
            }
        }
        return Pair(posTraits.toList(), negTraits.toList())
    }

    fun getActivePositiveTraits(): List<PositiveTraits> {
        return positiveTraitRepository.getActiveTraits()
    }

    fun getActiveNegativeTraits(): List<NegativeTraits> {
        return negativeTraitRepository.getActiveTraits()
    }

    fun createPositiveTrait(dto: NewTraitDto) {
        positiveTraitRepository.createTrait(dto)
    }

    fun createNegativeTrait(dto: NewTraitDto) {
        negativeTraitRepository.createTrait(dto)
    }

    fun getTraitsByScientistId(id: Long): ScientistTraitsDto {
        val posTraits = positiveTraitRepository.findTraitsByScientistId(id)
        val negTraits = negativeTraitRepository.findTraitsByScientistId(id)

        return ScientistTraitsDto(
            scientistId = id,
            positiveTraits = posTraits.map { traitMapper.fromPosPojoToDto(it) }.toList(),
            negativeTraits = negTraits.map { traitMapper.fromNegPojoToDto(it) }.toList()
        )
    }
}
