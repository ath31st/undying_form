package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.service.StorageService
import sidim.doma.undying.service.monster.SetBodyPartsService

@RestController
@Validated
@RequestMapping("/api/v1/set_body_parts")
class SetBodyPartsController(
    private val setBodyPartsService: SetBodyPartsService,
    private val storageService: StorageService,
    private val bodyPartsService: SetBodyPartsService
) {
    @PostMapping("/update_slots")
    fun updateSetBodyParts(@RequestBody dto: SetBodyPartsUpdateDto): ResponseEntity<HttpStatus> {
        setBodyPartsService.checkIsSetBodyPartsExistById(dto.setBodyPartsId)
        setBodyPartsService.checkUniqueBodyPartsInUpdateDto(dto)
        val scholarId = setBodyPartsService.getScholarIdBySetBodyParts(dto.setBodyPartsId)
        storageService.checkExistsBodyPartIdsInStorageByScholarId(dto, scholarId)
        val bpIdsForDeletingAndUpdateing = setBodyPartsService.updateSlotsSetBodyParts(dto)


        return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}