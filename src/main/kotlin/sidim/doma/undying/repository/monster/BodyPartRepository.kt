package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record1
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.dto.bodyparts.NewLegDto
import sidim.doma.undying.generated.tables.records.HandsRecord
import sidim.doma.undying.generated.tables.references.HANDS
import sidim.doma.undying.generated.tables.references.HEADS
import sidim.doma.undying.generated.tables.references.LEGS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.generated.tables.references.UPPER_BODIES
import sidim.doma.undying.model.Hand
import sidim.doma.undying.model.Leg

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
        return r.into(
            Hand(
                id = r.handId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.handTemplateId ?: 0,
                side = r.side ?: ""
            )
        )
    }

    fun saveGeneratedLegInStorage(dto: NewLegDto): Leg {
        val r = dslContext.newRecord(l)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.side = dto.side
        r.legTemplateId = dto.legTemplateId
        r.storageId = dto.storageId

        r.store()
        return r.into(
            Leg(
                id = r.legId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.legTemplateId ?: 0,
                side = r.side ?: ""
            )
        )
    }

    fun findHandsByStorageId(storageId: Long): List<Hand> {
        return dslContext.select(ha)
            .from(ha)
            .join(s)
            .on(s.STORAGE_ID.eq(ha.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map(::mapHandFromRecord)
    }

    private fun mapHandFromRecord(r: Record1<HandsRecord>): Hand {
        return Hand(
            id = r.value1()[ha.HAND_ID] ?: 0,
            quality = r.value1()[ha.QUALITY] ?: 0,
            integrity = r.value1()[ha.INTEGRITY] ?: 0,
            storageId = r.value1()[ha.STORAGE_ID],
            setBodyPartsId = r.value1()[ha.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r.value1()[ha.HAND_TEMPLATE_ID] ?: 0,
            side = r.value1()[ha.SIDE] ?: ""
        )
    }
}