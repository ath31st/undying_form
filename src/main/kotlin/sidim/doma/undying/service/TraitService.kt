package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.repository.NegativeTraitRepository
import sidim.doma.undying.repository.PositiveTraitRepository

@Service
class TraitService(
    private val positiveTraitRepository: PositiveTraitRepository,
    private val negativeTraitRepository: NegativeTraitRepository
) {
    fun getAllPositiveTraits(): List<PositiveTraits> {
        return positiveTraitRepository.getAllTraits()
    }

    fun getAllNegativeTraits(): List<NegativeTraits> {
        return negativeTraitRepository.getAllTraits()
    }
}