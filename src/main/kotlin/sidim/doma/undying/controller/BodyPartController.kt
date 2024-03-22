package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.bodyparts.TransferBodyPartsDto
import sidim.doma.undying.service.ActionService
import sidim.doma.undying.service.StorageService
import sidim.doma.undying.service.monster.BodyPartService

@RestController
@Validated
@RequestMapping("/api/v1/body_parts")
class BodyPartController(
    private val bodyPartService: BodyPartService,
    private val actionService: ActionService,
    private val storageService: StorageService,
) {
    @PostMapping("/transfer_body_parts_to_storage")
    @Transactional
    fun transferBodyPartsToStorage(@RequestBody dto: TransferBodyPartsDto): ResponseEntity<HttpStatus> {
        actionService.checkIfNoExistsPlayerAction(dto.scholarId, dto.actionUuid)
        val storageId = storageService.getStorageIdByScholarId(dto.scholarId)
        bodyPartService.transferBodyPartsFromScholarToStorage(dto, storageId)
        bodyPartService.deleteExtraBodyPartsAfterTransfer(dto.scholarId)
        actionService.deleteActionUuidByScholarId(dto.scholarId, dto.actionUuid)
        return ResponseEntity.ok(HttpStatus.OK)
    }

    @DeleteMapping("/delete_body_parts_from_storage")
    fun deleteBodyPartsFromStorage(@RequestBody dto: TransferBodyPartsDto): ResponseEntity<HttpStatus> {
        actionService.checkIfNoExistsPlayerAction(dto.scholarId, dto.actionUuid)
        val storageId = storageService.getStorageIdByScholarId(dto.scholarId)
        bodyPartService.deleteBodyPartsFromStorage(storageId, dto.bodyPartIds)
        return ResponseEntity.ok(HttpStatus.NO_CONTENT)
    }
}
