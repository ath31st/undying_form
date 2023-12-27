package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Hideouts
import sidim.doma.undying.repository.HideoutRepository

@Service
class HideoutService(
    private val namingService: NamingService,
    private val hideoutRepository: HideoutRepository
) {
    fun createHideout(): Hideouts {
        val name = namingService.generateHideoutName()
        return hideoutRepository.createHideout(name)
    }
}