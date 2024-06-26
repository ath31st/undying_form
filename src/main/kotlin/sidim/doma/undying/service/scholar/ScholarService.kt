package sidim.doma.undying.service.scholar

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.scholar.ScholarDto
import sidim.doma.undying.exceptionhandler.exception.ScholarException
import sidim.doma.undying.generated.tables.pojos.Scholars
import sidim.doma.undying.repository.scholar.ScholarRepository
import sidim.doma.undying.service.location.HideoutService
import sidim.doma.undying.service.NamingService
import sidim.doma.undying.service.monster.MonsterService
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.ScholarConstants.BONUSES_SUM
import sidim.doma.undying.util.constant.ScholarConstants.DEFAULT_EXPERIENCE
import sidim.doma.undying.util.constant.ScholarConstants.DEFAULT_MENTAL_HEALTH
import sidim.doma.undying.util.constant.ScholarConstants.DEFAULT_PHYSICAL_HEALTH
import sidim.doma.undying.util.constant.ScholarConstants.MAX_AGE
import sidim.doma.undying.util.constant.ScholarConstants.MIN_AGE
import sidim.doma.undying.util.constant.ScholarConstants.MIN_BONUS_VALUE
import sidim.doma.undying.util.constant.ScholarConstants.SKILLS_COUNT

@Service
class ScholarService(
    private val scholarRepository: ScholarRepository,
    private val hideoutService: HideoutService,
    private val namingService: NamingService,
    private val educationService: EducationService,
    private val generatorRandomValuesUtil: GeneratorRandomValuesUtil,
    private val monsterService: MonsterService,
) {
    fun createScholar(): Scholars {
        val randomValues = generatorRandomValuesUtil.generateRandomValues(
            SKILLS_COUNT, MIN_BONUS_VALUE, BONUSES_SUM
        )
        val dto = ScholarDto(
            name = namingService.generateScholarFirstLastName(),
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
            hideoutId = hideoutService.createHideout().hideoutId.let { it ?: 0 },
            monsterId = monsterService.createMonster().monsterId.let { it ?: 0 }
        )
        return scholarRepository.saveScholar(dto)
    }

    fun checkExistsScholarById(scholarId: Long) {
        if (!scholarRepository.existsScholarWithId(scholarId)) {
            throw ScholarException("Scholar with id: $scholarId not found", HttpStatus.NOT_FOUND)
        }
    }
}
