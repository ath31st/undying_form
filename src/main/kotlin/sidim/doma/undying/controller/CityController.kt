package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.citiy.NewCityDto
import sidim.doma.undying.service.location.CityService

@RestController
@Validated
@RequestMapping("/api/v1/cities")
class CityController(val cityService: CityService) {

    @PostMapping("/new_city")
    fun createCity(@RequestBody dto: NewCityDto): ResponseEntity<HttpStatus> {
        cityService.createCity(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}