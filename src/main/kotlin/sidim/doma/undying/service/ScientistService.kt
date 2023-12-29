package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.scientist.ScientistDto
import sidim.doma.undying.generated.tables.pojos.Scientists
import sidim.doma.undying.repository.ScientistRepository
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.ScientistConstants.BONUSES_SUM
import sidim.doma.undying.util.constant.ScientistConstants.DEFAULT_EXPERIENCE
import sidim.doma.undying.util.constant.ScientistConstants.DEFAULT_MENTAL_HEALTH
import sidim.doma.undying.util.constant.ScientistConstants.DEFAULT_PHYSICAL_HEALTH
import sidim.doma.undying.util.constant.ScientistConstants.MAX_AGE
import sidim.doma.undying.util.constant.ScientistConstants.MIN_AGE
import sidim.doma.undying.util.constant.ScientistConstants.SKILLS_COUNT

@Service
class ScientistService(
    private val scientistRepository: ScientistRepository,
    private val hideoutService: HideoutService,
    private val namingService: NamingService,
    private val educationService: EducationService,
    private val generatorRandomValuesUtil: GeneratorRandomValuesUtil
) {
    fun createScientist(): Scientists {
        val countSkills = SKILLS_COUNT
        val sumBonuses = BONUSES_SUM
        val randomValues =
            generatorRandomValuesUtil.generateRandomValues(countSkills, sumBonuses)
        val dto = ScientistDto(
            name = namingService.generateCaretakerFirstLastName(),
            age = generatorRandomValuesUtil.generateRandomInteger(MIN_AGE, MAX_AGE),
            physicalHealth = DEFAULT_PHYSICAL_HEALTH,
            mentalHealth = DEFAULT_MENTAL_HEALTH,
            experience = DEFAULT_EXPERIENCE,
            biology = randomValues[0],
            alchemy = randomValues[1],
            engineering = randomValues[2],
            successfulExperiments = 0,
            educationId = educationService.getRandomEducationId(),
            specializationId = null,
            hideoutId = hideoutService.createHideout().hideoutId.let { it ?: 0 }
        )
        return scientistRepository.createScientist(dto)
    }
}
