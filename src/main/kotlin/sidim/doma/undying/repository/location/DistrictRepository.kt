package sidim.doma.undying.repository.location

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.DISTRICTS

@Repository
class DistrictRepository(private val dslContext: DSLContext) {
    private val d = DISTRICTS

    fun getRandomDistrictId(): Int? {
        return dslContext.select(d.DISTRICT_ID)
            .from(d)
            .orderBy(DSL.rand())
            .limit(1)
            .fetchOneInto(Int::class.java)
    }
}
