package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Hideouts
import sidim.doma.undying.generated.tables.references.HIDEOUTS

@Repository
class HideoutRepository(
    private val dslContext: DSLContext
) {
    fun createHideout(name: String, randomValues: List<Int>): Hideouts {
        val r = dslContext.newRecord(HIDEOUTS)
        r.name = name
        r.alchemyEquipment = randomValues[0]
        r.biologyEquipment = randomValues[1]
        r.engineeringEquipment = randomValues[2]
        r.districtId = 1 // todo change this hardcode to service

        r.store()
        return r.into(Hideouts::class.java)
    }
}