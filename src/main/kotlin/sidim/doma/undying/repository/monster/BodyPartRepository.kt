package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record1
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewHandDto
import sidim.doma.undying.dto.bodyparts.NewHeadDto
import sidim.doma.undying.dto.bodyparts.NewLegDto
import sidim.doma.undying.dto.bodyparts.NewUpperBodyDto
import sidim.doma.undying.generated.tables.records.HandsRecord
import sidim.doma.undying.generated.tables.records.HeadsRecord
import sidim.doma.undying.generated.tables.records.LegsRecord
import sidim.doma.undying.generated.tables.records.UpperBodiesRecord
import sidim.doma.undying.generated.tables.references.HANDS
import sidim.doma.undying.generated.tables.references.HEADS
import sidim.doma.undying.generated.tables.references.LEGS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.generated.tables.references.UPPER_BODIES
import sidim.doma.undying.model.Hand
import sidim.doma.undying.model.Head
import sidim.doma.undying.model.Leg
import sidim.doma.undying.model.UpperBody

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

    fun saveGeneratedUpperBodyInStorage(dto: NewUpperBodyDto): UpperBody {
        val r = dslContext.newRecord(ub)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.upperBodyTemplateId = dto.upperBodyTemplateId
        r.storageId = dto.storageId

        r.store()
        return r.into(
            UpperBody(
                id = r.upperBodyId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.upperBodyTemplateId ?: 0,
            )
        )
    }

    fun saveGeneratedHeadInStorage(dto: NewHeadDto): Head {
        val r = dslContext.newRecord(he)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.headTemplateId = dto.headTemplateId
        r.storageId = dto.storageId

        r.store()
        return r.into(
            Head(
                id = r.headId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.headTemplateId ?: 0,
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

    fun findLegsByStorageId(storageId: Long): List<Leg> {
        return dslContext.select(l)
            .from(l)
            .join(s)
            .on(s.STORAGE_ID.eq(l.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map(::mapLegFromRecord)
    }

    private fun mapLegFromRecord(r: Record1<LegsRecord>): Leg {
        return Leg(
            id = r.value1()[l.LEG_ID] ?: 0,
            quality = r.value1()[l.QUALITY] ?: 0,
            integrity = r.value1()[l.INTEGRITY] ?: 0,
            storageId = r.value1()[l.STORAGE_ID],
            setBodyPartsId = r.value1()[l.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r.value1()[l.LEG_TEMPLATE_ID] ?: 0,
            side = r.value1()[l.SIDE] ?: ""
        )
    }

    fun findUpperBodiesByStorageId(storageId: Long): List<UpperBody> {
        return dslContext.select(ub)
            .from(ub)
            .join(s)
            .on(s.STORAGE_ID.eq(ub.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map(::mapUpperBodyFromRecord)
    }

    private fun mapUpperBodyFromRecord(r: Record1<UpperBodiesRecord>): UpperBody {
        return UpperBody(
            id = r.value1()[ub.UPPER_BODY_ID] ?: 0,
            quality = r.value1()[ub.QUALITY] ?: 0,
            integrity = r.value1()[ub.INTEGRITY] ?: 0,
            storageId = r.value1()[ub.STORAGE_ID],
            setBodyPartsId = r.value1()[ub.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r.value1()[ub.UPPER_BODY_TEMPLATE_ID] ?: 0,
        )
    }

    fun findHeadsByStorageId(storageId: Long): List<Head> {
        return dslContext.select(he)
            .from(he)
            .join(s)
            .on(s.STORAGE_ID.eq(he.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map(::mapHeadFromRecord)
    }

    private fun mapHeadFromRecord(r: Record1<HeadsRecord>): Head {
        return Head(
            id = r.value1()[he.HEAD_ID] ?: 0,
            quality = r.value1()[he.QUALITY] ?: 0,
            integrity = r.value1()[he.INTEGRITY] ?: 0,
            storageId = r.value1()[he.STORAGE_ID],
            setBodyPartsId = r.value1()[he.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r.value1()[he.HEAD_TEMPLATE_ID] ?: 0,
        )
    }
}