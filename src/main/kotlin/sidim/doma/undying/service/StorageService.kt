package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.repository.StorageRepository

@Service
class StorageService(
    private val storageRepository: StorageRepository
) {
    fun createStorage(): Storages {
        return storageRepository.saveStorage()
    }
}
