package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record1
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.bodyparts.NewBodyPartDto
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.STORAGES
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.util.BodyPartGroup

@Repository
class BodyPartRepository(private val dslContext: DSLContext) {
    private val bp = BODY_PARTS
    private val s = STORAGES

    fun saveGeneratedHandInStorage(dto: NewBodyPartDto): BodyPart {
        val r = dslContext.newRecord(bp)
        r.quality = dto.quality
        r.integrity = dto.integrity
        r.side = dto.side
        r.bodyPartTemplateId = dto.templateId
        r.storageId = dto.storageId

        r.store()
        return r.into(
            BodyPart(
                id = r.bodyPartId ?: 0,
                quality = r.quality ?: 0,
                integrity = r.integrity ?: 0,
                storageId = r.storageId,
                setBodyPartsId = r.setBodyPartsId,
                bodyPartTemplateId = r.bodyPartTemplateId ?: 0,
                bodyPartGroup = BodyPartGroup.HANDS,
                side = r.side
            )
        )
    }

    fun findBodyPartsByStorageId(storageId: Long): List<BodyPart> {
        return dslContext.select(bp)
            .from(bp)
            .join(s)
            .on(s.STORAGE_ID.eq(bp.STORAGE_ID))
            .where(s.STORAGE_ID.eq(storageId))
            .fetch()
            .map(::mapBodyPartFromRecord)
    }

    private fun mapBodyPartFromRecord(r: Record1<BodyPartsRecord>): BodyPart {
        return BodyPart(
            id = r.value1()[bp.BODY_PART_ID] ?: 0,
            quality = r.value1()[bp.QUALITY] ?: 0,
            integrity = r.value1()[bp.INTEGRITY] ?: 0,
            storageId = r.value1()[bp.STORAGE_ID],
            setBodyPartsId = r.value1()[bp.SET_BODY_PARTS_ID],
            bodyPartTemplateId = r.value1()[bp.BODY_PART_TEMPLATE_ID] ?: 0,
            side = r.value1()[bp.SIDE],
            bodyPartGroup = BodyPartGroup.HANDS, // todo rework
        )
    }
}
