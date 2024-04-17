package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Cities
import sidim.doma.undying.generated.tables.references.CITIES

@Repository
class CityRepository(private val dslContext: DSLContext) {
    private val c = CITIES

    fun saveNewCity(): Cities {
        val r = dslContext.newRecord(c)
        // todo add dto and fill the fields

        r.store()
        return r.into(Cities::class.java)
    }
}