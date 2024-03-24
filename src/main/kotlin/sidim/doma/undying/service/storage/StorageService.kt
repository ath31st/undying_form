package sidim.doma.undying.service.storage

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.setbodyparts.SetBodyPartsUpdateDto
import sidim.doma.undying.exceptionhandler.exception.StorageException
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.mapper.StorageMapper
import sidim.doma.undying.model.Storage
import sidim.doma.undying.repository.storage.StorageRepository
import sidim.doma.undying.util.constant.StorageConstants.CAPACITY

@Service
class StorageService(
    private val storageRepository: StorageRepository,
    private val storageMapper: StorageMapper,
) {
    fun createStorage(): Storages {
        return storageRepository.saveNewStorage(CAPACITY)
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

    fun getStorageIdByScholarId(scholarId: Long): Long {
        return storageRepository.findStorageByScholarId(scholarId)?.storageId ?: throw StorageException(
            "Storage bounded with scholar id: $scholarId not found.",
            HttpStatus.NOT_FOUND
        )
    }

    fun getCountEmptySlotsByStorageId(storageId: Long): Int {
        return storageRepository.getCountEmptySlotsByStorageId(storageId)
    }

    fun increaseCountEmptySlotsByStorageId(storageId: Long, changeCount: Int) {
        storageRepository.updateCountEmptySlotsByStorageId(storageId, changeCount)
    }

    fun decreaseCountEmptySlotsByStorageId(storageId: Long, changeCount: Int) {
        storageRepository.updateCountEmptySlotsByStorageId(storageId, -(changeCount))
    }

    fun checkExistsBodyPartIdsInStorageByScholarId(dto: SetBodyPartsUpdateDto, scholarId: Long) {
        if (!storageRepository.existsBodyPartIdsInStorageByScholarId(dto, scholarId)) {
            throw StorageException("In the storage absent some body parts from dto.", HttpStatus.BAD_REQUEST)
        }
    }

    fun checkCountEmptySlotsForTransferBodyPartsByStorageId(storageId: Long, neededSlots: Int) {
        val emptySlots = getCountEmptySlotsByStorageId(storageId)
        if (emptySlots < 0) {
            throw StorageException("Storage with id: $storageId not found.", HttpStatus.NOT_FOUND)
        } else if (emptySlots - neededSlots < 0) {
            throw StorageException(
                "There is not enough empty slots in the storage with id: $storageId. "
                        + "There are $emptySlots, $neededSlots are needed", HttpStatus.BAD_REQUEST
            )
        }
    }
}
