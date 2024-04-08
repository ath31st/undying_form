package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.dto.bodyparts.TransferBodyPartsDto
import sidim.doma.undying.mapper.BodyPartMapper
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
    private val bodyPartMapper: BodyPartMapper,
) {
    private fun generateRandomBodyPartByGraveyardId(scholarId: Long, graveyardId: Int): Long {
        val bodyPartGroup = BodyPartGroup.entries.random()
        var side: String? = null
        if (bodyPartGroup == BodyPartGroup.HANDS || bodyPartGroup == BodyPartGroup.LEGS) {
            side = listOf(LEFT_SIDE, RIGHT_SIDE).random()
        }

        val dto = NewBodyPartDto(
            scholarId = scholarId,
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
            templateId = bodyPartTemplateService.getRandomBodyPartTemplateIdByGraveyardIdAndBodyPartGroup(
                graveyardId,
                bodyPartGroup.ordinal
            ),
        )

        return bodyPartRepository.saveGeneratedBodyPart(dto)
    }

    fun generateRandomBodyPartsByGraveyardId(
        scholarId: Long,
        graveyardId: Int,
        countBodyParts: Int
    ): List<BodyPart> {
        val generatedBodyParts = mutableListOf<Long>()
        for (i in 0 until countBodyParts) {
            generatedBodyParts.add(generateRandomBodyPartByGraveyardId(scholarId, graveyardId))
        }

        return findBodyPartsByIds(generatedBodyParts)
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        val bodyPartRecords3 = bodyPartRepository.findBodyPartsByStorageId(storageId)
        return bodyPartRecords3.map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }

    fun findBodyPartsByScholarId(scholarId: Long): List<BodyPart> {
        val bodyPartRecords3 = bodyPartRepository.findBodyPartsByScholarId(scholarId)
        return bodyPartRecords3.map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }

    fun findBodyPartsByIds(listIds: List<Long>): List<BodyPart> {
        val bodyPartRecords3 = bodyPartRepository.findBodyPartsByIds(listIds)
        return bodyPartRecords3.map { bodyPartMapper.fromBodyPartRecordToModel(it.value1(), it.value2(), it.value3()) }
    }

    fun transferBodyPartsFromScholarToStorage(dto: TransferBodyPartsDto, storageId: Long) {
        bodyPartRepository.updateBodyPartLocationToStorageWithStorageId(
            dto.bodyPartIds.toSet(),
            storageId
        )
    }

    fun deleteExtraBodyPartsAfterTransfer(scholarId: Long) {
        bodyPartRepository.deleteExtraBodyPartsWithScholarId(scholarId)
    }

    fun deleteExtraBodyPartsAfterUpdateSet(idsForDeleting: List<Long>) {
        bodyPartRepository.deleteExtraBodyPartsAfterUpdateSet(idsForDeleting)
    }

    fun transferBodyPartsFromStorageToSet(idsForTransferring: List<Long>, setBodyPartsId: Long) {
        bodyPartRepository.updateBodyPartLocationToSetWithSetId(idsForTransferring, setBodyPartsId)
    }

    fun deleteBodyPartsFromStorage(storageId: Long, bodyPartIds: List<Long>): Int {
        return bodyPartRepository.deleteBodyPartsFromStorage(storageId, bodyPartIds)
    }
}
