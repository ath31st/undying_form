package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.repository.monster.SetBodyPartsRepository

@Service
class SetBodyPartsService(private val setBodyPartsRepository: SetBodyPartsRepository) {
    fun createEmptySet(): SetsBodyParts {
        return setBodyPartsRepository.saveEmptySetBodyParts()
    }
}
