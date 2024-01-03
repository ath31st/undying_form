package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.repository.NegativeTraitRepository
import sidim.doma.undying.repository.PositiveTraitRepository
import sidim.doma.undying.util.constant.TraitConstants.COUNT_TRAITS
import sidim.doma.undying.util.constant.TraitConstants.TRAITS_WEIGHT_SUM

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository,
) {
    fun generateAndSaveRandomSetTraits(scientistId: Long) {
        val posTraits = collectScientistRandomTraits().first
        val negTraits = collectScientistRandomTraits().second

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
            val posTrait = getAllPositiveTraits()
                .shuffled()
                .firstOrNull {
                    weight - it.weight!! >= 1 && traitGroupIds.contains(it.traitGroupId).not()
                }
            posTrait?.let {
                weight -= it.weight!!
                traitGroupIds.add(it.traitGroupId!!)
                posTraits.add(posTrait)
            }

            if ((posTraits.size + negTraits.size) < COUNT_TRAITS && weight > 0) {
                val negTrait = getAllNegativeTraits()
                    .shuffled()
                    .firstOrNull {
                        weight - it.weight!! >= 0 && traitGroupIds.contains(it.traitGroupId).not()
                    }
                negTrait?.let {
                    weight -= negTrait.weight!!
                    traitGroupIds.add(negTrait.traitGroupId!!)
                    negTraits.add(negTrait)
                }
            }
        }
        return Pair(posTraits.toList(), negTraits.toList())
    }

    fun getAllPositiveTraits(): List<PositiveTraits> {
        return positiveTraitRepository.getAllTraits()
    }

    fun getAllNegativeTraits(): List<NegativeTraits> {
        return negativeTraitRepository.getAllTraits()
    }
}
