package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Hideouts
import sidim.doma.undying.repository.HideoutRepository
import sidim.doma.undying.util.GeneratorRandomValuesService

@Service
class HideoutService(
    private val namingService: NamingService,
    private val hideoutRepository: HideoutRepository,
    private val generatorRandomValuesService: GeneratorRandomValuesService
) {
    fun createHideout(): Hideouts {
        val name = namingService.generateHideoutName()
        val countEquipment = 3
        val sumBonuses = 8
        val randomValues =
            generatorRandomValuesService.generateRandomValues(countEquipment, sumBonuses)

        return hideoutRepository.createHideout(name, randomValues)
    }
}