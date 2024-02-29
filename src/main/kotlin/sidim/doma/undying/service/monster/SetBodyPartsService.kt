package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
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
}
