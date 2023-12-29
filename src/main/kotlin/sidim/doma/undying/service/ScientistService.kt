package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.scientist.ScientistDto
import sidim.doma.undying.generated.tables.pojos.Scientists
import sidim.doma.undying.repository.ScientistRepository
import sidim.doma.undying.util.GeneratorRandomValuesUtil

@Service
class ScientistService(
    private val scientistRepository: ScientistRepository,
    private val hideoutService: HideoutService,
    private val namingService: NamingService,
    private val generatorRandomValuesUtil: GeneratorRandomValuesUtil
) {
    fun createScientist(): Scientists {
        val countSkills = 3
        val sumBonuses = 12
        val randomValues =
            generatorRandomValuesUtil.generateRandomValues(countSkills, sumBonuses)
        val dto = ScientistDto(
            name = namingService.generateCaretakerFirstLastName(),
            age = generatorRandomValuesUtil.generateRandomInteger(18, 60),
            physicalHealth = 100,
            mentalHealth = 100,
            experience = 0,
            biology = randomValues[0],
            alchemy = randomValues[1],
            engineering = randomValues[2],
            successfulExperiments = 0,
            educationId = 1, // todo add this random education
            specializationId = null,
            hideoutId = hideoutService.createHideout().hideoutId!!
        )
        return scientistRepository.createScientist(dto)
    }
}
