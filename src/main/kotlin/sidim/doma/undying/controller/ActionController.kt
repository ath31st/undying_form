package sidim.doma.undying.controller

import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.action.NewFindingBodyPartsInGraveyardReq
import sidim.doma.undying.model.BodyPart
import sidim.doma.undying.service.ActionService
import sidim.doma.undying.util.ActionTypes
import sidim.doma.undying.util.constant.ActionConstants.DURATION_FINDING_BODY_PARTS

@RestController
@Validated
@RequestMapping("/api/v1/actions")
class ActionController(
    private val actionService: ActionService,
) {
    @PostMapping("/find_body_parts")
    fun findBodyPartsInGraveyard(@RequestBody req: NewFindingBodyPartsInGraveyardReq): ResponseEntity<HttpStatus> {
        actionService.checkIfExistsPlayerAction(req.scholarId, req.actionUuid, ActionTypes.FINDING_BODY_PARTS)
        actionService.generateRandomBodyPartsByGraveyardForScholar(req.graveyardId, req.scholarId)
        actionService.savePlayerAction(
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
        actionService.checkStatusPlayerAction(scholarId, actionUuid)
        return ResponseEntity.ok(actionService.getResultFindingBodyParts(scholarId))
    }
}
