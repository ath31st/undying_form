package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.repository.SetsBodyPartsRepository

@Service
class SetsBodyPartsService(private val setsBodyPartsRepository: SetsBodyPartsRepository) {
    fun createEmptySet(): SetsBodyParts {
        return setsBodyPartsRepository.createEmptySetBodyParts()
    }
}