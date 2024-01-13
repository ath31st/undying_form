package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.model.Hand
import sidim.doma.undying.repository.monster.BodyPartRepository

@Service
class BodyPartService(private val bodyPartRepository: BodyPartRepository) {

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        val bodyParts = mutableListOf<BodyPart>()
        bodyParts.addAll(findHandsByStorageId(storageId))

        return bodyParts
    }

    fun findHandsByStorageId(storageId: Long): List<Hand> {
        return bodyPartRepository.findHandsByStorageId(storageId)
    }
}