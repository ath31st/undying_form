package sidim.doma.undying.service.storage

import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.item.NewItemDto
import sidim.doma.undying.exceptionhandler.exception.ItemException
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.repository.storage.ItemRepository

@Service
class ItemService(private val itemRepository: ItemRepository) {
    fun createItem(dto: NewItemDto): Items {
        checkExistsItemByName(dto.name)
        return itemRepository.saveNewItem(dto)
    }

    fun getAllItemsWithSearch(req: PageRequest, name: String?): PageDto<Items> {
        return itemRepository.getItemsWithPaginationAndSorting(req, name)
    }

    fun checkExistsItemByName(name: String) {
        if (itemRepository.isItemExistByName(name)) {
            throw ItemException("Item with name $name already exists", HttpStatus.CONFLICT)
        }
    }
}