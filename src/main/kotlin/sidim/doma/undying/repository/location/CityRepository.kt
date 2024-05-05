package sidim.doma.undying.repository.location

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.citiy.NewCityDto
import sidim.doma.undying.generated.tables.pojos.Cities
import sidim.doma.undying.generated.tables.references.CITIES
import sidim.doma.undying.repository.CommonRepositoryMethods

@Repository
class CityRepository(
    private val dslContext: DSLContext,
    private val commonRepositoryMethods: CommonRepositoryMethods,
) {
    private val c = CITIES

    fun saveNewCity(dto: NewCityDto): Cities {
        val r = dslContext.newRecord(c)
        r.name = dto.name.trim()
        r.population = dto.population
        r.description = dto.description.trim()
        r.foundationYear = dto.foundationYear
        r.flagUrl = dto.flagUrl.trim()

        r.store()
        return r.into(Cities::class.java)
    }

    fun findCityById(cityId: Int): Cities? {
        return dslContext.select(c)
            .from(c)
            .where(c.CITY_ID.eq(cityId))
            .fetchOneInto(Cities::class.java)
    }

    fun isCityExistByName(cityName: String): Boolean {
        return commonRepositoryMethods.isRecordExistByStringField(dslContext, c, c.NAME, cityName)
    }
}
