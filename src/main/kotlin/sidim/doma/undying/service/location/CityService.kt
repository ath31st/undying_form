package sidim.doma.undying.service.location

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.citiy.NewCityDto
import sidim.doma.undying.exceptionhandler.exception.CityException
import sidim.doma.undying.generated.tables.pojos.Cities
import sidim.doma.undying.repository.location.CityRepository

@Service
class CityService(private val cityRepository: CityRepository) {
    private val logger = KotlinLogging.logger {}

    fun getCityById(cityId: Int): Cities {
        return cityRepository.findCityById(cityId)
            ?: throw CityException("City with id: $cityId not found", HttpStatus.NOT_FOUND)
    }

    fun createCity(dto: NewCityDto): Cities {
        checkCityExistByName(dto.name.trim())
        logger.info { "Creating new city: $dto" }
        return cityRepository.saveNewCity(dto)
    }

    fun checkCityExistByName(cityName: String) {
        if (cityRepository.isCityExistByName(cityName)) {
            throw CityException("City with name: $cityName already exists", HttpStatus.CONFLICT)
        }
    }
}