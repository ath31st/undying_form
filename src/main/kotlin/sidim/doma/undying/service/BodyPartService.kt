package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.repository.BodyPartRepository

@Service
class BodyPartService(private val bodyPartRepository: BodyPartRepository) {
}