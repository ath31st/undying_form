package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Hideouts
import sidim.doma.undying.repository.HideoutRepository
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.HideoutConstants.BONUSES_SUM
import sidim.doma.undying.util.constant.HideoutConstants.COUNT_EQUIPMENT
import sidim.doma.undying.util.constant.HideoutConstants.MIN_BONUS_VALUE

@Service
class HideoutService(
    private val namingService: NamingService,
    private val hideoutRepository: HideoutRepository,
    private val generatorRandomValuesUtil: GeneratorRandomValuesUtil,
    private val districtService: DistrictService,
) {
    fun createHideout(): Hideouts {
        val name = namingService.generateHideoutName()
        val randomValues = generatorRandomValuesUtil.generateRandomValues(
            COUNT_EQUIPMENT, MIN_BONUS_VALUE, BONUSES_SUM
        )
        val districtId = districtService.getRandomDistrictId()

        return hideoutRepository.createHideout(name, districtId, randomValues)
    }
}
