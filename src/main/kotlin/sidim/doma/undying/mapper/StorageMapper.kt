package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.model.Storage

@Component
class StorageMapper {
    fun fromStoragePojoToModel(pojo: Storages): Storage {
        val bodyParts = mutableListOf<BodyPart>()
        return Storage(pojo.storageId ?: 0, pojo.capacity ?: 0, bodyParts)
    }
}