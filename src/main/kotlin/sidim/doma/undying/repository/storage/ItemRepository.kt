package sidim.doma.undying.repository.storage

import org.jooq.DSLContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.item.NewItemDto
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.generated.tables.references.ITEMS
import sidim.doma.undying.generated.tables.references.ITEMS_STORAGES
import sidim.doma.undying.repository.CommonRepositoryMethods

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
        return CommonRepositoryMethods.isRecordExistByStringField(dslContext, i, i.NAME, name)
    }

    fun getItemsWithPaginationAndSorting(req: PageRequest, name: String?): PageDto<Items> {
        val offset = req.offset

        val selectCondition = if (name != null) i.NAME.likeIgnoreCase("%${name.trim()}%") else null

        val totalElements = dslContext.selectCount()
            .from(i)
            .apply { if (selectCondition != null) where(selectCondition) }
            .fetchOneInto(Int::class.java) ?: 0

        val fieldName = req.sort.get().toList().firstOrNull()?.property?.let { i.field(it) } ?: i.ITEM_ID
        val sortField =
            if (req.sort.get().anyMatch { it.direction == Sort.Direction.ASC }) fieldName.asc() else fieldName.desc()

        val items = dslContext.selectFrom(i)
            .apply { if (selectCondition != null) where(selectCondition) }
            .orderBy(sortField)
            .limit(req.pageSize)
            .offset(offset)
            .fetchInto(Items::class.java)

        return CommonRepositoryMethods.createPageDto(req, totalElements, items)
    }
}
