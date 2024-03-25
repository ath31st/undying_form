package sidim.doma.undying.service.storage

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.item.NewItemDto
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.repository.storage.ItemRepository

@Service
class ItemService(private val itemRepository: ItemRepository) {
    fun createItem(dto: NewItemDto): Items {
        return itemRepository.saveNewItem(dto)
    }

    fun getAllItems(req: PageRequest): PageDto<Items> {
        return itemRepository.getAllItems(req)
    }
}