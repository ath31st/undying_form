package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS

@Repository
class SetsBodyPartsRepository(private val dslContext: DSLContext) {
    private val sbp = SETS_BODY_PARTS
}