package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.generated.tables.references.HANDS
import sidim.doma.undying.generated.tables.references.HEADS
import sidim.doma.undying.generated.tables.references.LEGS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.generated.tables.references.UPPER_BODIES
import sidim.doma.undying.model.Hand

@Repository
class BodyPartRepository(private val dslContext: DSLContext) {
    private val ha = HANDS
    private val l = LEGS
    private val ub = UPPER_BODIES
    private val he = HEADS
    private val s = STORAGES

    fun saveGeneratedHandInStorage(dto: NewHandDto): Hand {
        val r = dslContext.newRecord(ha)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.side = dto.side
        r.handTemplateId = dto.handTemplateId
        r.storageId = dto.storageId

        r.store()
        return r.into(Hand::class.java)
    }

    fun findHandsByStorageId(storageId: Long): List<Hand> {
        return dslContext.select(ha)
            .from(ha)
            .join(s)
            .on(s.STORAGE_ID.eq(ha.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetchInto(Hand::class.java)
        //.fetchInto(Hands::class.java)
    }
}