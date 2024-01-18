package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_HIGH_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_LOW_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_MID_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.HIGH_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.LOW_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.MID_VALUE_CAUNT

@Service
class ActionService(
    private val storageService: StorageService,
    private val bodyPartsService: BodyPartService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun findBodyPartsInGraveyard(
        graveyardId: Int,
        scholarId: Long
    ): List<BodyPart> {
        val bodyPartsCount = generator.generateRandomWithProbabilities(
            LOW_VALUE_CAUNT,
            CHANCE_LOW_VALUE_CAUNT,
            MID_VALUE_CAUNT,
            CHANCE_MID_VALUE_CAUNT,
            HIGH_VALUE_CAUNT,
            CHANCE_HIGH_VALUE_CAUNT
        )

        val foundedBodyParts = bodyPartsService.generateRandomBodyPartsByGraveyardId(
            graveyardId,
            bodyPartsCount
        )

        return foundedBodyParts
    }
}