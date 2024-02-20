package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Storages
import sidim.doma.undying.generated.tables.references.HIDEOUTS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.util.constant.StorageConstants.CAPACITY

@Repository
class StorageRepository(private val dslContext: DSLContext) {
    private val st = STORAGES
    private val h = HIDEOUTS
    private val sc = SCHOLARS

    fun saveStorage(): Storages {
        val r = dslContext.newRecord(st)
        r.capacity = CAPACITY

        r.store()
        return r.into(Storages::class.java)
    }

    fun findStorageByScholarId(scholarId: Long): Storages? {
        return dslContext.select(st)
            .from(st)
            .join(h).on(st.STORAGE_ID.eq(h.STORAGE_ID))
            .join(sc).on(h.HIDEOUT_ID.eq(sc.HIDEOUT_ID))
            .where(sc.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Storages::class.java)
    }
}
