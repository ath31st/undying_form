package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.storage.DeleteBodyPartsDto
import sidim.doma.undying.model.Storage
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.service.storage.StorageService

@RestController
@Validated
@RequestMapping("/api/v1/storages")
class StorageController(
    private val storageService: StorageService,
    private val bodyPartService: BodyPartService,
) {
    @GetMapping("/storage_contents_by_scholar_id/{id}")
    fun getStorageContentsByScholarId(@PathVariable id: Long): ResponseEntity<Storage> {
        return ResponseEntity.ok(storageService.getStorageByScholarId(id))
    }

    @DeleteMapping("/delete_body_parts_from_storage")
    fun deleteBodyPartsFromStorage(@RequestBody dto: DeleteBodyPartsDto): ResponseEntity<HttpStatus> {
        val storageId = storageService.getStorageIdByScholarId(dto.scholarId)
        val countDeletedBodyParts = bodyPartService.deleteBodyPartsFromStorage(storageId, dto.bodyPartIds)
        storageService.increaseCountEmptySlotsByStorageId(storageId, countDeletedBodyParts)
        return ResponseEntity.ok(HttpStatus.NO_CONTENT)
    }
}
