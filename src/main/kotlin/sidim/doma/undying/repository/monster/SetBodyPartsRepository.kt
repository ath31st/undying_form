package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.SetsBodyParts
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS

@Repository
class SetBodyPartsRepository(private val dslContext: DSLContext) {
    private val sbp = SETS_BODY_PARTS

    fun saveEmptySetBodyParts(): SetsBodyParts {
        val r = dslContext.newRecord(sbp)
        r.bodyPartsCount = 0
        r.bonusSet = 0
        r.setIsCompleted = false

        r.store()
        return r.into(SetsBodyParts::class.java)
    }
}
