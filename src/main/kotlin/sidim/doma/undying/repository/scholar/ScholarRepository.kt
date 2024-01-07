package sidim.doma.undying.repository.scholar

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.scholar.ScholarDto
import sidim.doma.undying.generated.tables.pojos.Scholars
import sidim.doma.undying.generated.tables.references.SCHOLARS

@Repository
class ScholarRepository(private val dslContext: DSLContext) {
    private val s = SCHOLARS

    fun saveScholar(dto: ScholarDto): Scholars {
        val r = dslContext.newRecord(s)
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
        return r.into(Scholars::class.java)
    }
}
