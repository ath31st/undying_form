package sidim.doma.undying.repository.storage

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.item.NewItemDto
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.generated.tables.references.ITEMS
import sidim.doma.undying.generated.tables.references.ITEMS_STORAGES

@Repository
class ItemRepository(private val dslContext: DSLContext) {
    private val i = ITEMS
    private val ist = ITEMS_STORAGES

    fun saveNewItem(dto: NewItemDto): Items {
        val r = dslContext.newRecord(i)
        r.name = dto.name
        r.description = dto.description
        r.rarity = dto.rarity

        r.store()
        return r.into(Items::class.java)
    }

    fun getAllItems(pageNumber: Int, size: Int): PageDto<Items> {
        return PageDto(
            content = ,
            totalElements = ,
            totalPages = ,
            currentNumberPage = pageNumber
        )
    }
}