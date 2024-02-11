package sidim.doma.undying.service

import java.util.UUID
import org.springframework.stereotype.Service
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.repository.PlayerActionRepository
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.service.scholar.ScholarService
import sidim.doma.undying.util.ActionTypes
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_HIGH_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_LOW_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_MID_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.HIGH_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.LOW_VALUE_CAUNT
import sidim.doma.undying.util.constant.ActionConstants.MID_VALUE_CAUNT

@Service
class ActionService(
    private val playerActionRepository: PlayerActionRepository,
    private val storageService: StorageService,
    private val bodyPartsService: BodyPartService,
    private val scholarService: ScholarService,
    private val graveyardService: GraveyardService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun checkUuidAction(scholarId: Long, uuid: UUID) {

    }

    fun savePlayerAction(scholarId: Long, uuid: UUID, actionType: ActionTypes, duration: Long) {
        playerActionRepository.savePlayerAction(scholarId, uuid, actionType, duration)
    }

    fun generateRandomBodyPartsByGraveyardForScholar(
        graveyardId: Int,
        scholarId: Long
    ): List<BodyPart> {
        scholarService.checkExistsScholarById(scholarId)
        graveyardService.checkExistsGraveyardWithId(graveyardId)

        val bodyPartsCount = generator.generateRandomWithProbabilities(
            LOW_VALUE_CAUNT,
            CHANCE_LOW_VALUE_CAUNT,
            MID_VALUE_CAUNT,
            CHANCE_MID_VALUE_CAUNT,
            HIGH_VALUE_CAUNT,
            CHANCE_HIGH_VALUE_CAUNT
        )

        val foundedBodyParts = bodyPartsService.generateRandomBodyPartsByGraveyardId(
            scholarId,
            graveyardId,
            bodyPartsCount
        )

        return foundedBodyParts
    }
}