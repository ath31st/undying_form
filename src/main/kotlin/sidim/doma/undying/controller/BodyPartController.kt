package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.bodyparts.TransferBodyPartsDto
import sidim.doma.undying.service.PlayerActionService
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.service.storage.StorageService

@RestController
@Validated
@RequestMapping("/api/v1/body_parts")
class BodyPartController(
    private val bodyPartService: BodyPartService,
    private val playerActionService: PlayerActionService,
    private val storageService: StorageService,
) {
    @PostMapping("/transfer_body_parts_to_storage")
    @Transactional
    fun transferBodyPartsToStorage(@RequestBody dto: TransferBodyPartsDto): ResponseEntity<HttpStatus> {
        playerActionService.checkIfNoExistsPlayerAction(dto.scholarId, dto.actionUuid)
        val storageId = storageService.getStorageIdByScholarId(dto.scholarId)
        storageService.checkCountEmptySlotsForTransferBodyPartsByStorageId(storageId, dto.bodyPartIds.size)
        bodyPartService.transferBodyPartsFromScholarToStorage(dto, storageId)
        storageService.decreaseCountEmptySlotsByStorageId(storageId, dto.bodyPartIds.size)
        bodyPartService.deleteExtraBodyPartsAfterTransfer(dto.scholarId)
        playerActionService.deleteActionUuidByScholarId(dto.scholarId, dto.actionUuid)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}
