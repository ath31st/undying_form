package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.repository.monster.BodyPartRepository
import sidim.doma.undying.util.BodyPartGroup
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
    private fun generateRandomBodyPartByGraveyardId(graveyardId: Int, storageId: Long): BodyPart {
        val bodyPartGroup = BodyPartGroup.entries.random()
        var side: String? = null
        if (bodyPartGroup == BodyPartGroup.HANDS || bodyPartGroup == BodyPartGroup.LEGS) {
            side = listOf(LEFT_SIDE, RIGHT_SIDE).random()
        }

        val dto = NewBodyPartDto(
            quality = generator.generateRandomWithProbabilities(
                LOW_QUALITY,
                CHANCE_LOW_QUALITY,
                MID_QUALITY,
                CHANCE_MID_QUALITY,
                HIGH_QUALITY,
                CHANCE_HIGH_QUALITY
            ),
            integrity = generator.generateRandomInteger(MIN_INTEGRITY, MAX_INTEGRITY),
            side = side,
            templateId = bodyPartsTemplateService.getRandomHandTemplateIdByGraveyardId(graveyardId),
            storageId = storageId
        )

        return bodyPartRepository.saveGeneratedBodyPartInStorage(dto)
    }

    fun generateRandomBodyPartsByGraveyardIdAndSaveInStorage(
        graveyardId: Int,
        storageId: Long,
        countBodyParts: Int
    ): List<BodyPart> {
        val generatedBodyParts = mutableListOf<BodyPart>()
        for (i in 0 until countBodyParts) {
            generatedBodyParts.add(generateRandomBodyPartByGraveyardId(graveyardId, storageId))
        }

        return generatedBodyParts
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
