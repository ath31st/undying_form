package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.util.GeneratorRandomValuesUtil

@Service
class ActionService(
    private val storageService: StorageService,
    private val bodyPartsService: BodyPartService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun findBodyPartsInGraveyard(
        graveyardId: Int,
        scholarId: Long
    ) {
    }
}