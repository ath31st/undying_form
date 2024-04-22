package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.action.NewFindingBodyPartsInGraveyardReq
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.service.PlayerActionService
import sidim.doma.undying.util.ActionTypes
import sidim.doma.undying.util.constant.ActionConstants.DURATION_FINDING_BODY_PARTS
import java.util.*

@RestController
@Validated
@RequestMapping("/api/v1/player_actions")
class PlayerActionController(
    private val playerActionService: PlayerActionService,
) {
    @PostMapping("/find_body_parts")
    fun findBodyPartsInGraveyard(@RequestBody req: NewFindingBodyPartsInGraveyardReq): ResponseEntity<HttpStatus> {
        playerActionService.checkIfExistsPlayerAction(req.scholarId, req.actionUuid, ActionTypes.FINDING_BODY_PARTS)
        playerActionService.generateRandomBodyPartsByGraveyardForScholar(req.graveyardId, req.scholarId)
        playerActionService.savePlayerAction(
            req.scholarId, req.actionUuid,
            ActionTypes.FINDING_BODY_PARTS, DURATION_FINDING_BODY_PARTS
        )
        return ResponseEntity.ok(HttpStatus.OK)
    }

    @GetMapping("/find_body_parts")
    fun findBodyPartsInGraveyard(
        @RequestParam scholarId: Long,
        @RequestParam actionUuid: UUID
    ): ResponseEntity<List<BodyPart>> {
        playerActionService.checkStatusPlayerAction(scholarId, actionUuid)
        return ResponseEntity.ok(playerActionService.getResultFindingBodyParts(scholarId))
    }
}
