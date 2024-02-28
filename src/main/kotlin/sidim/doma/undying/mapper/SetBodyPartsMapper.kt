package sidim.doma.undying.mapper

import org.jooq.Record7
import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.records.BodyPartsRecord
import sidim.doma.undying.generated.tables.records.SetsBodyPartsRecord
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS
import sidim.doma.undying.model.SetBodyParts

@Component
class SetBodyPartsMapper(private val bodyPartMapper: BodyPartMapper) {
    private val sbp = SETS_BODY_PARTS

    fun fromRecordToModel(
        rec: Record7<SetsBodyPartsRecord,
                BodyPartsRecord,
                BodyPartsRecord,
                BodyPartsRecord,
                BodyPartsRecord,
                BodyPartsRecord,
                BodyPartsRecord>
    ): SetBodyParts {
        return SetBodyParts(
            setBodyPartsId = rec.value1()[sbp.SET_BODY_PARTS_ID] ?: 0,
            bodyPartsCount = rec.value1()[sbp.BODY_PARTS_COUNT] ?: 0,
            setIsCompleted = rec.value1()[sbp.SET_IS_COMPLETED] ?: false,
            bonusSet = rec.value1()[sbp.BONUS_SET] ?: 0,
            leftHandSlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value2()),
            rightHandSlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value3()),
            leftLegSlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value4()),
            rightLegSlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value5()),
            upperBodySlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value6()),
            headSlot = bodyPartMapper.fromBodyPartRecordToShortModel(rec.value7()),
        )
    }
}
