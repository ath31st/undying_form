package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exception.SpecializationException
import sidim.doma.undying.repository.SpecializationRepository

@Service
class SpecializationService(private val specializationRepository: SpecializationRepository) {
    fun getRandomSpecializationId(): Int {
        return specializationRepository.getRandomSpecializationId()
    }

    private fun checkIsSpecializationExistById(id: Int) {
        if (!specializationRepository.isSpecializationExistById(id)) {
            throw SpecializationException(
                "Specialization with id $id not found",
                HttpStatus.NOT_FOUND
            )
        }
    }
}