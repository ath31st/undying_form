package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.records.SetsBodyPartsRecord
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS
import sidim.doma.undying.model.SetBodyParts

@Component
class SetBodyPartsMapper {
    private val sbp = SETS_BODY_PARTS
    fun fromRecordToModel(r1: SetsBodyPartsRecord): SetBodyParts {
        return SetBodyParts(
            setBodyPartsId = r1[sbp.SET_BODY_PARTS_ID] ?: 0,
            bodyPartsCount = r1[sbp.BODY_PARTS_COUNT] ?: 0,
            setIsCompleted = r1[sbp.SET_IS_COMPLETED] ?: false,
            bonusSet = r1[sbp.BONUS_SET] ?: 0,
            leftHandSlot = null,
            rightLegSlot = null,
            upperBodySlot = null,
            headSlot = null,
            //todo add here body parts mapper
        )
    }
}
