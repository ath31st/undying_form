package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.service.monster.SocialClassService

@RestController
@Validated
@RequestMapping("/api/v1/social_class")
class SocialClassController(
    private val socialClassService: SocialClassService
) {
    @PostMapping("/new_social_class")
    fun createPositiveTrait(@RequestBody dto: NewSocialClassDto): ResponseEntity<HttpStatus> {
        socialClassService.createSocialClass(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}
