package sidim.doma.undying.repository.location

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Hideouts
import sidim.doma.undying.generated.tables.references.HIDEOUTS

@Repository
class HideoutRepository(private val dslContext: DSLContext) {
    private val h = HIDEOUTS

    fun saveHideout(
        name: String,
        storageId: Long,
        districtId: Int,
        randomValues: List<Int>
    ): Hideouts {
        val r = dslContext.newRecord(h)
        r.name = name.trim()
        r.alchemyEquipment = randomValues[0]
        r.biologyEquipment = randomValues[1]
        r.engineeringEquipment = randomValues[2]
        r.storageId = storageId
        r.districtId = districtId

        r.store()
        return r.into(Hideouts::class.java)
    }
}
