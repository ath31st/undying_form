package sidim.doma.undying.service.monster

import kotlin.reflect.KFunction2
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.dto.bodyparts.NewLegDto
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.model.Hand
import sidim.doma.undying.model.Leg
import sidim.doma.undying.repository.monster.BodyPartRepository
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.BodyPartConstants.CHANCE_HIGH_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.CHANCE_LOW_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.CHANCE_MID_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.HIGH_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.LEFT_SIDE
import sidim.doma.undying.util.constant.BodyPartConstants.LOW_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.MAX_INTEGRITY
import sidim.doma.undying.util.constant.BodyPartConstants.MID_QUALITY
import sidim.doma.undying.util.constant.BodyPartConstants.MIN_INTEGRITY
import sidim.doma.undying.util.constant.BodyPartConstants.RIGHT_SIDE

@Service
class BodyPartService(
    private val bodyPartRepository: BodyPartRepository,
    private val bodyPartsTemplateService: BodyPartsTemplateService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun generateRandomHandByGraveyardId(graveyardId: Int, storageId: Long): Hand {
        val handDto = NewHandDto(
            quality = generator.generateRandomWithProbabilities(
                LOW_QUALITY,
                CHANCE_LOW_QUALITY,
                MID_QUALITY,
                CHANCE_MID_QUALITY,
                HIGH_QUALITY,
                CHANCE_HIGH_QUALITY
            ),
            integrity = generator.generateRandomInteger(MIN_INTEGRITY, MAX_INTEGRITY),
            side = listOf(LEFT_SIDE, RIGHT_SIDE).random(),
            handTemplateId = bodyPartsTemplateService.getRandomHandTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedHandInStorage(handDto)
    }

    fun generateRandomLegByGraveyardId(graveyardId: Int, storageId: Long): Leg {
        val legDto = NewLegDto(
            quality = generator.generateRandomWithProbabilities(
                LOW_QUALITY,
                CHANCE_LOW_QUALITY,
                MID_QUALITY,
                CHANCE_MID_QUALITY,
                HIGH_QUALITY,
                CHANCE_HIGH_QUALITY
            ),
            integrity = generator.generateRandomInteger(MIN_INTEGRITY, MAX_INTEGRITY),
            side = listOf(LEFT_SIDE, RIGHT_SIDE).random(),
            legTemplateId = bodyPartsTemplateService.getRandomLegTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedLegInStorage(legDto)
    }

    fun generateRandomBodyPartsByGraveyardIdAndSaveInStorage(graveyardId: Int, storageId: Long, countBodyParts: Int) {
        val generateBodyParts: List<KFunction2<Int, Long, BodyPart>> =
            listOf(
                ::generateRandomHandByGraveyardId,
                ::generateRandomLegByGraveyardId,
            )

        generateBodyParts.forEach { println(it.invoke(graveyardId, storageId)) } // todo delete this
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        generateRandomBodyPartsByGraveyardIdAndSaveInStorage(2, 1, 1) // todo delete this
        val bodyParts = mutableListOf<BodyPart>()
        bodyParts.addAll(findHandsByStorageId(storageId))

        return bodyParts
    }

    fun findHandsByStorageId(storageId: Long): List<Hand> {
        return bodyPartRepository.findHandsByStorageId(storageId)
    }
}
