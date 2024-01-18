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
    private val bodyPartTemplateService: BodyPartTemplateService,
    private val generator: GeneratorRandomValuesUtil,
) {
    private fun generateRandomBodyPartByGraveyardId(graveyardId: Int): Long {
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
            templateId = bodyPartTemplateService.getRandomBodyPartTemplateIdByGraveyardId(graveyardId),
        )

        return bodyPartRepository.saveGeneratedBodyPart(dto)
    }

    fun generateRandomBodyPartsByGraveyardId(
        graveyardId: Int,
        countBodyParts: Int
    ): List<BodyPart> {
        val generatedBodyParts = mutableListOf<Long>()
        for (i in 0 until countBodyParts) {
            generatedBodyParts.add(generateRandomBodyPartByGraveyardId(graveyardId))
        }

        return findBodyPartsByIds(generatedBodyParts)
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        return bodyPartRepository.findBodyPartsByStorageId(storageId)
    }

    fun findBodyPartsByIds(listIds: List<Long>): List<BodyPart> {
        return bodyPartRepository.findBodyPartsByIds(listIds)
    }
}
