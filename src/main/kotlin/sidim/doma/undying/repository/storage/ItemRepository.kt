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
        val offset = pageNumber * size
        val totalElements = dslContext.fetchCount(i)

        val items = dslContext.selectFrom(i)
            .orderBy(i.ITEM_ID.asc())
            .limit(size)
            .offset(offset)
            .fetchInto(Items::class.java)

        val totalPages = if (totalElements % size == 0) {
            totalElements / size
        } else {
            (totalElements / size) + 1
        }

        return PageDto(
            content = items,
            totalElements = totalElements,
            totalPages = totalPages,
            currentNumberPage = pageNumber
        )
    }
}