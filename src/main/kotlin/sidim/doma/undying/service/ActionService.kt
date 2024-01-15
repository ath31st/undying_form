package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.model.BodyPart
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
    ): List<BodyPart> {
        val storage = storageService.getStorageByScholarId(scholarId)
        val bodyPartsCount = generator.generateRandomWithProbabilities(1, 50, 2, 35, 3, 15) // todo add constants
        val foundedBodyParts = bodyPartsService.generateRandomBodyPartsByGraveyardIdAndSaveInStorage(
            graveyardId,
            storage.storageId,
            bodyPartsCount
        )
        return foundedBodyParts
    }
}