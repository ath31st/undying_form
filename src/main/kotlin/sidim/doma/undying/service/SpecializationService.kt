package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.repository.SpecializationRepository

@Service
class SpecializationService(private val specializationRepository: SpecializationRepository) {
    fun getRandomSpecializationId(): Int {
        return specializationRepository.getRandomSpecializationId()
    }
}