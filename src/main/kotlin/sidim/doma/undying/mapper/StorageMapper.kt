package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.model.Storage
import sidim.doma.undying.service.monster.BodyPartService

@Component
class StorageMapper(private val bodyPartService: BodyPartService) {
    fun fromStoragePojoToModel(pojo: Storages): Storage {
        val bodyParts = bodyPartService.findBodyPartsByStorageId(pojo.storageId ?: 0).toMutableList()
        return Storage(pojo.storageId ?: 0, pojo.capacity ?: 0, pojo.emptySlots ?: 0, bodyParts)
    }
}