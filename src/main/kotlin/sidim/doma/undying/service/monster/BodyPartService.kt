package sidim.doma.undying.service.monster

import kotlin.reflect.KFunction2
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.dto.bodyparts.NewHeadDto
import sidim.doma.undying.dto.bodyparts.NewLegDto
import sidim.doma.undying.dto.bodyparts.NewUpperBodyDto
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.model.Hand
import sidim.doma.undying.model.Head
import sidim.doma.undying.model.Leg
import sidim.doma.undying.model.UpperBody
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

    fun generateRandomUpperBodyByGraveyardId(graveyardId: Int, storageId: Long): UpperBody {
        val upperBodyDto = NewUpperBodyDto(
            quality = generator.generateRandomWithProbabilities(
                LOW_QUALITY,
                CHANCE_LOW_QUALITY,
                MID_QUALITY,
                CHANCE_MID_QUALITY,
                HIGH_QUALITY,
                CHANCE_HIGH_QUALITY
            ),
            integrity = generator.generateRandomInteger(MIN_INTEGRITY, MAX_INTEGRITY),
            upperBodyTemplateId = bodyPartsTemplateService.getRandomUpperBodyTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedUpperBodyInStorage(upperBodyDto)
    }

    fun generateRandomHeadByGraveyardId(graveyardId: Int, storageId: Long): Head {
        val headDto = NewHeadDto(
            quality = generator.generateRandomWithProbabilities(
                LOW_QUALITY,
                CHANCE_LOW_QUALITY,
                MID_QUALITY,
                CHANCE_MID_QUALITY,
                HIGH_QUALITY,
                CHANCE_HIGH_QUALITY
            ),
            integrity = generator.generateRandomInteger(MIN_INTEGRITY, MAX_INTEGRITY),
            headTemplateId = bodyPartsTemplateService.getRandomHeadTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedHeadInStorage(headDto)
    }

    fun generateRandomBodyPartsByGraveyardIdAndSaveInStorage(graveyardId: Int, storageId: Long, countBodyParts: Int) {
        val generateBodyParts: List<KFunction2<Int, Long, BodyPart>> =
            listOf(
                ::generateRandomHandByGraveyardId,
                ::generateRandomLegByGraveyardId,
                ::generateRandomUpperBodyByGraveyardId,
                ::generateRandomHeadByGraveyardId
            )
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        val bodyParts = mutableListOf<BodyPart>()
        bodyParts.addAll(findHandsByStorageId(storageId))
        bodyParts.addAll(findLegsByStorageId(storageId))
        bodyParts.addAll(findUpperBodiesByStorageId(storageId))
        bodyParts.addAll(findHeadsByStorageId(storageId))

        return bodyParts
    }

    private fun findHandsByStorageId(storageId: Long): List<Hand> {
        return bodyPartRepository.findHandsByStorageId(storageId)
    }

    private fun findLegsByStorageId(storageId: Long): List<Leg> {
        return bodyPartRepository.findLegsByStorageId(storageId)
    }

    private fun findUpperBodiesByStorageId(storageId: Long): List<UpperBody> {
        return bodyPartRepository.findUpperBodiesByStorageId(storageId)
    }

    private fun findHeadsByStorageId(storageId: Long): List<Head> {
        return bodyPartRepository.findHeadsByStorageId(storageId)
    }
}
