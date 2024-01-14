package sidim.doma.undying.service.monster

import kotlin.reflect.KFunction2
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.model.Hand
import sidim.doma.undying.repository.monster.BodyPartRepository
import sidim.doma.undying.util.GeneratorRandomValuesUtil

@Service
class BodyPartService(
    private val bodyPartRepository: BodyPartRepository,
    private val bodyPartsTemplateService: BodyPartsTemplateService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun generateRandomHandByGraveyardId(graveyardId: Int, storageId: Long): Hand {
        val handDto = NewHandDto(
            quality = generator.generateRandomWithProbabilities(1, 75, 2, 20, 3, 5), // TODO rework this generation
            integrity = generator.generateRandomInteger(85, 100), // TODO rework this generation
            side = listOf("left", "right").random(),
            handTemplateId = bodyPartsTemplateService.getRandomHandTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedHandInStorage(handDto)
    }

    fun generateRandomBodyPartsByGraveyardIdAndSaveInStorage(graveyardId: Int, storageId: Long, countBodyParts: Int) {
        val generateBodyParts: List<KFunction2<Int, Long, Hand>> =
            listOf(::generateRandomHandByGraveyardId)
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        val bodyParts = mutableListOf<BodyPart>()
        bodyParts.addAll(findHandsByStorageId(storageId))

        return bodyParts
    }

    fun findHandsByStorageId(storageId: Long): List<Hand> {
        return bodyPartRepository.findHandsByStorageId(storageId)
    }
}
