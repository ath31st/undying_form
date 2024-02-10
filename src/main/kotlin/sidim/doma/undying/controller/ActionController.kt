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

@RestController
@Validated
@RequestMapping("/api/v1/actions")
class ActionController(
    private val actionService: ActionService,
) {
    @PostMapping("/find_body_parts")
    fun findBodyPartsInGraveyard(@RequestBody req: NewFindingBodyPartsInGraveyardReq): ResponseEntity<HttpStatus> {
        actionService.checkUuidAction(req.uuid)
        actionService.generateRandomBodyPartsByGraveyardForScholar(req.graveyardId, req.scholarId)
        return ResponseEntity.ok(HttpStatus.OK)
    }

    @GetMapping("/find_body_parts")
    fun findBodyPartsInGraveyard(
        @RequestParam scholarId: Long,
        @RequestParam uuid: UUID
    ): ResponseEntity<List<BodyPart>> {
        actionService.checkUuidAction(req.uuid)
        return ResponseEntity.ok(actionService.getResultFindingBodyParts(scholarId))
    }
}
