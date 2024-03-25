package sidim.doma.undying.repository.storage

import org.jooq.DSLContext
import org.springframework.data.domain.PageRequest
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
        r.name = dto.name.trim()
        r.description = dto.description.trim()
        r.rarity = dto.rarity

        r.store()
        return r.into(Items::class.java)
    }

    fun isItemExistByName(name: String): Boolean {
        return dslContext.selectCount()
            .from(i)
            .where(i.NAME.eq(name.trim()))
            .fetchOneInto(Int::class.java) == 1
    }

    fun getAllItems(req: PageRequest): PageDto<Items> {
        val offset = req.offset
        val totalElements = dslContext.fetchCount(i)

        val items = dslContext.selectFrom(i)
            .orderBy(i.ITEM_ID.asc())
            .limit(req.pageSize)
            .offset(offset)
            .fetchInto(Items::class.java)

        val totalPages = if (totalElements % req.pageSize == 0) {
            totalElements / req.pageSize
        } else {
            (totalElements / req.pageSize) + 1
        }

        return PageDto(
            content = items,
            totalElements = totalElements,
            totalPages = totalPages,
            currentNumberPage = req.pageNumber + 1
        )
    }
}