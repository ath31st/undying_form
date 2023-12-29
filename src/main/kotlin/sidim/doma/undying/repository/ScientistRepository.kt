package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.scientist.ScientistDto
import sidim.doma.undying.generated.tables.pojos.Scientists
import sidim.doma.undying.generated.tables.references.SCIENTISTS

@Repository
class ScientistRepository(private val dslContext: DSLContext) {
    fun createScientist(dto: ScientistDto): Scientists {
        val r = dslContext.newRecord(SCIENTISTS)
        r.name = dto.name
        r.age = dto.age
        r.physicalHealth = dto.physicalHealth
        r.mentalHealth = dto.mentalHealth
        r.experience = dto.experience
        r.biology = dto.biology
        r.alchemy = dto.alchemy
        r.engineering = dto.engineering
        r.successfulExperiments = dto.successfulExperiments
        r.educationId = dto.educationId
        r.specializationId = dto.specializationId
        r.hideoutId = dto.hideoutId

        r.store()
        return r.into(Scientists::class.java)
    }
}
