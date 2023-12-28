package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.DISTRICTS

@Repository
class DistrictRepository(private val dslContext: DSLContext) {
    fun getRandomDistrictId(): Int {
        return dslContext.select(DISTRICTS.DISTRICT_ID)
            .from(DISTRICTS)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java) ?: throw IllegalStateException("No districts found")
    }
}