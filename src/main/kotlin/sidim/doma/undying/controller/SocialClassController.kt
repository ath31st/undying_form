package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.service.monster.SocialClassService

@RestController
@Validated
@RequestMapping("/api/v1/social_classes")
class SocialClassController(
    private val socialClassService: SocialClassService
) {
    @GetMapping("/social_classes_by_active")
    fun getSocialClassesByActive(@RequestParam isActive: Boolean?): ResponseEntity<List<SocialClasses>> {
        return ResponseEntity.ok(socialClassService.getSocialClasses(isActive))
    }

    @PostMapping("/new_social_class")
    fun createSocialClass(@RequestBody dto: NewSocialClassDto): ResponseEntity<HttpStatus> {
        socialClassService.createSocialClass(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}
