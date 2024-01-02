package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.mapper.NegativeTraitMapper
import sidim.doma.undying.mapper.PositiveTraitMapper
import sidim.doma.undying.model.Trait
import sidim.doma.undying.repository.NegativeTraitRepository
import sidim.doma.undying.repository.PositiveTraitRepository
import sidim.doma.undying.util.constant.TraitConstants.TRAITS_WEIGHT_SUM

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository,
    private val positiveTraitMapper: PositiveTraitMapper,
    private val negativeTraitMapper: NegativeTraitMapper
) {
    fun collectScientistRandomTraits(scientistId: Long) {
        var weight = TRAITS_WEIGHT_SUM
        val traits: MutableList<Trait> = mutableListOf()
        traits.addAll(getAllPositiveTraits()
            .map { positiveTraitMapper.fromPojoToModel(it) }
            .toList())
        traits.addAll(getAllNegativeTraits()
            .map { negativeTraitMapper.fromPojoToModel(it) }
            .toList())
    }

    fun getAllPositiveTraits(): List<PositiveTraits> {
        return positiveTraitRepository.getAllTraits()
    }

    fun getAllNegativeTraits(): List<NegativeTraits> {
        return negativeTraitRepository.getAllTraits()
    }
}
