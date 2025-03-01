package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.PlayerActionException
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.repository.PlayerActionRepository
import sidim.doma.undying.service.location.GraveyardService
import sidim.doma.undying.service.monster.BodyPartService
import sidim.doma.undying.service.scholar.ScholarService
import sidim.doma.undying.util.ActionTypes
import sidim.doma.undying.util.GeneratorRandomValuesUtil
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_HIGH_VALUE_COUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_LOW_VALUE_COUNT
import sidim.doma.undying.util.constant.ActionConstants.CHANCE_MID_VALUE_COUNT
import sidim.doma.undying.util.constant.ActionConstants.HIGH_VALUE_COUNT
import sidim.doma.undying.util.constant.ActionConstants.LOW_VALUE_COUNT
import sidim.doma.undying.util.constant.ActionConstants.MID_VALUE_COUNT
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.math.absoluteValue

@Service
class PlayerActionService(
    private val playerActionRepository: PlayerActionRepository,
    private val bodyPartsService: BodyPartService,
    private val scholarService: ScholarService,
    private val graveyardService: GraveyardService,
    private val generator: GeneratorRandomValuesUtil,
) {
    fun checkIfExistsPlayerAction(scholarId: Long, actionUuid: UUID, actionType: ActionTypes) {
        if (playerActionRepository.existsPlayerActionWithActionTypeAndScholarId(scholarId, actionType)) {
            throw PlayerActionException(
                "Player action for scholar ID: $scholarId with action type: ${actionType.name} already exists",
                HttpStatus.CONFLICT
            )
        }
        if (playerActionRepository.existsPlayerActionWithUuidAndScholarId(scholarId, actionUuid)) {
            throw PlayerActionException(
                "Player action for scholar ID: $scholarId with uuid: $actionUuid already exists",
                HttpStatus.CONFLICT
            )
        }
    }

    fun checkIfNoExistsPlayerAction(scholarId: Long, actionUuid: UUID) {
        if (!playerActionRepository.existsPlayerActionWithUuidAndScholarId(scholarId, actionUuid)) {
            throw PlayerActionException(
                "Player action for scholar ID: $scholarId with uuid: $actionUuid not found",
                HttpStatus.NOT_FOUND
            )
        }
    }

    fun checkStatusPlayerAction(scholarId: Long, actionUuid: UUID) {
        val optionPa = playerActionRepository.findPlayerActionByScholarIdAndUuid(scholarId, actionUuid)
        val pa = optionPa ?: throw PlayerActionException(
            "Player action for scholar ID: $scholarId and uuid: $actionUuid not found",
            HttpStatus.NOT_FOUND
        )

        val currentTime = LocalDateTime.now()
        if (pa.endAt!!.isAfter(currentTime)) {
            val duration = Duration.between(pa.endAt, currentTime)
            val minutesDifference = duration.toMinutes().absoluteValue

            throw PlayerActionException(
                "Player action for scholar ID: $scholarId and uuid: $actionUuid will be able after $minutesDifference minutes",
                HttpStatus.BAD_REQUEST
            )
        }
    }

    fun savePlayerAction(scholarId: Long, actionUuid: UUID, actionType: ActionTypes, duration: Long) {
        playerActionRepository.savePlayerAction(scholarId, actionUuid, actionType, duration)
    }

    fun generateRandomBodyPartsByGraveyardForScholar(
        graveyardId: Int,
        scholarId: Long
    ): List<BodyPart> {
        scholarService.checkExistsScholarById(scholarId)
        graveyardService.checkExistsGraveyardWithId(graveyardId)

        val bodyPartsCount = generator.generateRandomWithProbabilities(
            LOW_VALUE_COUNT,
            CHANCE_LOW_VALUE_COUNT,
            MID_VALUE_COUNT,
            CHANCE_MID_VALUE_COUNT,
            HIGH_VALUE_COUNT,
            CHANCE_HIGH_VALUE_COUNT
        )

        val foundedBodyParts = bodyPartsService.generateRandomBodyPartsByGraveyardId(
            scholarId,
            graveyardId,
            bodyPartsCount
        )

        return foundedBodyParts
    }

    fun getResultFindingBodyParts(scholarId: Long): List<BodyPart> {
        return bodyPartsService.findBodyParts(scholarId, BodyPartService.SearchingBy.SCHOLAR_ID)
    }

    fun deleteActionUuidByScholarId(scholarId: Long, actionUuid: UUID) {
        playerActionRepository.deletePlayerAction(scholarId, actionUuid)
    }
}