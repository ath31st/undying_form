package sidim.doma.undying.service

import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import kotlin.math.absoluteValue
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.PlayerActionException
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
    fun checkIfExistsPlayerAction(scholarId: Long, actionUuid: UUID) {
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

    fun getResultFindingBodyParts(scholarId: Long): List<BodyPart> {
        return bodyPartsService.findBodyPartsByScholarId(scholarId)
    }

    fun deleteActionUuidByScholarId(scholarId: Long, actionUuid: UUID) {
        playerActionRepository.deletePlayerAction(scholarId, actionUuid)
    }
}