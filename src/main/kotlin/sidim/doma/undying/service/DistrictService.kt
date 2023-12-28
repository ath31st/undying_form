package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.repository.DistrictRepository

@Service
class DistrictService(private val districtRepository: DistrictRepository) {
    fun getRandomDistrictId(): Int {
        return districtRepository.getRandomDistrictId()
    }
}