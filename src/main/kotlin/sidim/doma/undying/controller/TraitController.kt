package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.trait.NewTraitDto
import sidim.doma.undying.dto.trait.ScholarTraitsDto
import sidim.doma.undying.service.TraitService

@RestController
@Validated
@RequestMapping("/api/v1/traits")
class TraitController(
    private val traitService: TraitService
) {
    @PostMapping("/new_pos_trait")
    fun createPositiveTrait(@RequestBody dto: NewTraitDto): ResponseEntity<HttpStatus> {
        traitService.createPositiveTrait(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/new_neg_trait")
    fun createNegativeTrait(@RequestBody dto: NewTraitDto): ResponseEntity<HttpStatus> {
        traitService.createNegativeTrait(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/scholar/{id}")
    fun scholarTraits(@PathVariable id: Long): ResponseEntity<ScholarTraitsDto> {
        return ResponseEntity.ok(traitService.getTraitsByScholarId(id))
    }
}
