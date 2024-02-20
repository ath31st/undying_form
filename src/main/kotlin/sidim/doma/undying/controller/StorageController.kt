package sidim.doma.undying.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.model.Storage
import sidim.doma.undying.service.StorageService

@RestController
@Validated
@RequestMapping("/api/v1/storages")
class StorageController(
    private val storageService: StorageService,
) {
    @GetMapping("/storage_contents_by_scholar_id/{id}")
    fun getStorageContentsByScholarId(@PathVariable id: Long): ResponseEntity<Storage> {
        return ResponseEntity.ok(storageService.getStorageByScholarId(id))
    }
}
