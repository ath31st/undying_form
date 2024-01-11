package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.util.constant.StorageConstants.CAPACITY

@Repository
class StorageRepository(private val dslContext: DSLContext) {
    private val s = STORAGES

    fun saveStorage(): Storages {
        val r = dslContext.newRecord(s)
        r.capacity = CAPACITY

        r.store()
        return r.into(Storages::class.java)
    }
}