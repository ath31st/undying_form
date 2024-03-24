package sidim.doma.undying.repository.storage

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.generated.tables.references.ITEMS
import sidim.doma.undying.generated.tables.references.ITEMS_STORAGES

@Repository
class ItemRepository(private val dslContext: DSLContext) {
    private val i = ITEMS
    private val ist = ITEMS_STORAGES

    fun saveNewItem(): Items {
        val r = dslContext.newRecord(i)

        r.store()
        return r.into(Items::class.java)
    }
}