package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.storage.TransferBodyPartsDto
import sidim.doma.undying.service.ActionService
import sidim.doma.undying.service.monster.BodyPartService

@RestController
@Validated
@RequestMapping("/api/v1/body_parts")
class BodyPartController(
    private val bodyPartService: BodyPartService,
    private val actionService: ActionService,
) {
    @PostMapping("/transfer_body_parts_to_storage")
    @Transactional
    fun transferBodyPartsToStorage(@RequestBody dto: TransferBodyPartsDto): ResponseEntity<HttpStatus> {
        actionService.checkIfNoExistsPlayerAction(dto.scholarId, dto.actionUuid)
        bodyPartService.transferBodyPartsFromScholarToStorage(dto)
        bodyPartService.deleteExtraBodyPartsAfterTransfer(dto.scholarId)
        actionService.deleteActionUuidByScholarId(dto.scholarId, dto.actionUuid)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}
