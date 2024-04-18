package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.citiy.NewCityDto
import sidim.doma.undying.generated.tables.pojos.Cities
import sidim.doma.undying.generated.tables.references.CITIES

@Repository
class CityRepository(private val dslContext: DSLContext) {
    private val c = CITIES

    fun saveNewCity(dto: NewCityDto): Cities {
        val r = dslContext.newRecord(c)
        r.name = dto.name
        r.population = dto.population
        r.description = dto.description
        r.foundationYear = dto.foundationYear
        r.flagUrl = dto.flagUrl

        r.store()
        return r.into(Cities::class.java)
    }

    fun isCityExistByName(cityName: String): Boolean {
        return dslContext.selectCount()
            .from(c)
            .where(c.NAME.eq(cityName))
            .fetchOneInto(Int::class.java) == 1
    }
}