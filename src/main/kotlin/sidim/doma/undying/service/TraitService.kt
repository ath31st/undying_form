package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.mapper.NegativeTraitMapper
import sidim.doma.undying.mapper.PositiveTraitMapper
import sidim.doma.undying.model.NegativeTrait
import sidim.doma.undying.model.PositiveTrait
import sidim.doma.undying.model.Trait
import sidim.doma.undying.repository.NegativeTraitRepository
import sidim.doma.undying.repository.PositiveTraitRepository
import sidim.doma.undying.util.constant.TraitConstants.COUNT_TRAITS
import sidim.doma.undying.util.constant.TraitConstants.TRAITS_WEIGHT_SUM

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository,
    private val positiveTraitMapper: PositiveTraitMapper,
    private val negativeTraitMapper: NegativeTraitMapper
) {
    fun saveScientistTraits(scientistId: Long) {
        val traits = collectScientistRandomTraits()
        positiveTraitRepository.saveScientistPositiveTraits(
            scientistId,
            traits.filterIsInstance<PositiveTrait>().map { it.traitId() }.toList()
        )
        negativeTraitRepository.saveScientistNegativeTraits(
            scientistId,
            traits.filterIsInstance<NegativeTrait>().map { it.traitId() }.toList()
        )
    }

    fun collectScientistRandomTraits(): Set<Trait> {
        var weight = TRAITS_WEIGHT_SUM
        val traits = mutableSetOf<Trait>()
        val traitGroupIds = mutableSetOf<Int>()

        while (traits.size < COUNT_TRAITS && weight > 0) {
            val posTrait = getAllPositiveTraits()
                .shuffled()
                .firstOrNull {
                    weight - it.weight!! >= 1 && traitGroupIds.contains(it.traitGroupId).not()
                }
            posTrait?.let {
                weight -= it.weight!!
                traitGroupIds.add(it.traitGroupId!!)
                traits.add(positiveTraitMapper.fromPojoToModel(posTrait))
            }

            if (traits.size < COUNT_TRAITS && weight > 0) {
                val negTrait = getAllNegativeTraits()
                    .shuffled()
                    .firstOrNull {
                        weight - it.weight!! >= 0 && traitGroupIds.contains(it.traitGroupId).not()
                    }
                negTrait?.let {
                    weight -= negTrait.weight!!
                    traits.add(negativeTraitMapper.fromPojoToModel(negTrait))
                    traitGroupIds.add(negTrait.traitGroupId!!)
                }
            }
        }
        return traits
    }

    fun getAllPositiveTraits(): List<PositiveTraits> {
        return positiveTraitRepository.getAllTraits()
    }

    fun getAllNegativeTraits(): List<NegativeTraits> {
        return negativeTraitRepository.getAllTraits()
    }
}
