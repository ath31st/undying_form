package sidim.doma.undying.service.scholar

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.dto.trait.ScholarTraitsDto
import sidim.doma.undying.exceptionhandler.exception.ItemException
import sidim.doma.undying.exceptionhandler.exception.TraitException
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.mapper.TraitMapper
import sidim.doma.undying.repository.scholar.NegativeTraitRepository
import sidim.doma.undying.repository.scholar.PositiveTraitRepository
import sidim.doma.undying.util.constant.TraitConstants.COUNT_TRAITS
import sidim.doma.undying.util.constant.TraitConstants.TRAITS_WEIGHT_SUM

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository,
    private val traitMapper: TraitMapper
) {
    fun generateAndSaveRandomSetTraits(scholarId: Long) {
        val traits = collectScholarRandomTraits()
        val posTraits = traits.first
        val negTraits = traits.second

        positiveTraitRepository.saveScholarPositiveTraits(
            scholarId,
            posTraits.map { it.positiveTraitId ?: 0 }.toList()
        )
        negativeTraitRepository.saveScholarNegativeTraits(
            scholarId,
            negTraits.map { it.negativeTraitId ?: 0 }.toList()
        )
    }

    fun collectScholarRandomTraits(): Pair<List<PositiveTraits>, List<NegativeTraits>> {
        var weight = TRAITS_WEIGHT_SUM
        val posTraits = mutableSetOf<PositiveTraits>()
        val negTraits = mutableSetOf<NegativeTraits>()
        val traitGroupIds = mutableSetOf<Int>()

        while ((posTraits.size + negTraits.size) < COUNT_TRAITS && weight > 0) {
            val posTrait = getActivePositiveTraits()
                .shuffled()
                .firstOrNull {
                    weight - (it.weight ?: 0) >= 1 && !traitGroupIds.contains(it.traitGroupId ?: 0)
                }
            posTrait?.let {
                weight -= it.weight ?: 0
                traitGroupIds.add(it.traitGroupId ?: 0)
                posTraits.add(it)
            }

            if ((posTraits.size + negTraits.size) < COUNT_TRAITS && weight > 0) {
                val negTrait = getActiveNegativeTraits()
                    .shuffled()
                    .firstOrNull {
                        weight - (it.weight ?: 0) >= 0 && !traitGroupIds.contains(
                            it.traitGroupId ?: 0
                        )
                    }
                negTrait?.let {
                    weight -= it.weight ?: 0
                    traitGroupIds.add(it.traitGroupId ?: 0)
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
        checkExistsPositiveTraitByName(dto.name)
        positiveTraitRepository.saveNewTrait(dto)
    }

    fun checkExistsPositiveTraitByName(name: String) {
        if (positiveTraitRepository.isPositiveTraitExistByName(name)) {
            throw TraitException("Positive with name $name already exists", HttpStatus.CONFLICT)
        }
    }

    fun createNegativeTrait(dto: NewTraitDto) {
        checkExistsNegativeTraitByName(dto.name)
        negativeTraitRepository.saveNewTrait(dto)
    }

    fun checkExistsNegativeTraitByName(name: String) {
        if (negativeTraitRepository.isNegativeTraitExistByName(name)) {
            throw TraitException("Negative with name $name already exists", HttpStatus.CONFLICT)
        }
    }

    fun getTraitsByScholarId(id: Long): ScholarTraitsDto {
        val posTraits = positiveTraitRepository.findTraitsByScholarId(id)
        val negTraits = negativeTraitRepository.findTraitsByScholarId(id)

        return ScholarTraitsDto(
            scholarId = id,
            positiveTraits = posTraits.map { traitMapper.fromPosPojoToDto(it) }.toList(),
            negativeTraits = negTraits.map { traitMapper.fromNegPojoToDto(it) }.toList()
        )
    }
}
