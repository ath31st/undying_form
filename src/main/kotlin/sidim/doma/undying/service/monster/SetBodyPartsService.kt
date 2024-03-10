package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.exceptionhandler.exception.SetBodyPartsException
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.model.SetBodyParts
import sidim.doma.undying.repository.monster.SetBodyPartsRepository

@Service
class SetBodyPartsService(private val setBodyPartsRepository: SetBodyPartsRepository) {
    fun createEmptySet(): SetsBodyParts {
        return setBodyPartsRepository.saveEmptySetBodyParts()
    }

    fun checkIsSetBodyPartsExistById(id: Long) {
        if (!setBodyPartsRepository.existsSetBodyPartsWithId(id)) {
            throw SetBodyPartsException("Set body parts with id $id not found", HttpStatus.NOT_FOUND)
        }
    }

    fun getSetBodyPartsByMonsterId(monsterId: Long): SetBodyParts {
        return setBodyPartsRepository.findSetBodyPartsByMonsterId(monsterId)
            ?: throw SetBodyPartsException(
                "Set body parts for monster id: $monsterId not found",
                HttpStatus.NOT_FOUND
            )
    }

    fun getScholarIdBySetBodyParts(setBodyPartsId: Long): Long {
        return setBodyPartsRepository.findScholarIdBySetBodyPartsId(setBodyPartsId)
            ?: throw SetBodyPartsException(
                "Scholar id for set body parts with id $setBodyPartsId not found",
                HttpStatus.NOT_FOUND
            )
    }

    fun checkUniqueBodyPartsInUpdateDto(dto: SetBodyPartsUpdateDto) {
        val bodyParts = setOf(
            dto.leftHandIdForSlot,
            dto.rightHandIdForSlot,
            dto.leftLegIdForSlot,
            dto.rightLegIdForSlot,
            dto.upperBodyIdForSlot,
            dto.headIdForSlot
        )

        val distinctSize = bodyParts.filterNotNull().toSet().size

        if (distinctSize != bodyParts.filterNotNull().size) {
            throw SetBodyPartsException("Body parts in dto not unique", HttpStatus.BAD_REQUEST)
        }
    }

    fun updateSlotsSetBodyParts(dto: SetBodyPartsUpdateDto): Pair<List<Long>, List<Long>> {
        return setBodyPartsRepository.updateSlotsSetBodyParts(dto)
    }
}
