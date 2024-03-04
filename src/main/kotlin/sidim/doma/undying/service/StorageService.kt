package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.exceptionhandler.exception.StorageException
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.mapper.StorageMapper
import sidim.doma.undying.model.Storage
import sidim.doma.undying.repository.StorageRepository

@Service
class StorageService(
    private val storageRepository: StorageRepository,
    private val storageMapper: StorageMapper,
) {
    fun createStorage(): Storages {
        return storageRepository.saveStorage()
    }

    fun getStorageByScholarId(scholarId: Long): Storage {
        val storagePojo = storageRepository.findStorageByScholarId(scholarId)
        return storageMapper.fromStoragePojoToModel(
            storagePojo ?: throw StorageException(
                "Storage bounded with scholar id: $scholarId not found.",
                HttpStatus.NOT_FOUND
            )
        )
    }

    fun checkExistsBodyPartIdsInStorageByScholarId(dto: SetBodyPartsUpdateDto, scholarId: Long) {
        if (!storageRepository.existsBodyPartIdsInStorageByScholarId(dto, scholarId)) {
            throw StorageException("In storage absent some body parts from dto.", HttpStatus.BAD_REQUEST)
        }
    }
}
