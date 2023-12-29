package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exception.EducationException
import sidim.doma.undying.repository.EducationRepository

@Service
class EducationService(private val educationRepository: EducationRepository) {
    fun getRandomEducationId(): Int {
        return educationRepository.getRandomEducationId() ?: throw EducationException(
            "No education found",
            HttpStatus.NOT_FOUND
        )
    }
}
