package sidim.doma.undying.service.scholar

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.EducationException
import sidim.doma.undying.generated.tables.pojos.Education
import sidim.doma.undying.repository.scholar.EducationRepository

@Service
class EducationService(private val educationRepository: EducationRepository) {
    fun getRandomEducationId(): Int {
        return educationRepository.getRandomEducationId() ?: throw EducationException(
            "No education found",
            HttpStatus.NOT_FOUND
        )
    }

    fun getEducationByScholarId(scholarId: Long): Education {
        return educationRepository.findEducationByScholarId(scholarId)
            ?: throw EducationException("Education for scholar with id: $scholarId not found", HttpStatus.NOT_FOUND)
    }
}
