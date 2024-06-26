package sidim.doma.undying.service.location

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.DistrictException
import sidim.doma.undying.repository.location.DistrictRepository

@Service
class DistrictService(private val districtRepository: DistrictRepository) {
    fun getRandomDistrictId(): Int {
        return districtRepository.getRandomDistrictId() ?: throw DistrictException(
            "No districts found",
            HttpStatus.NOT_FOUND
        )
    }
}
