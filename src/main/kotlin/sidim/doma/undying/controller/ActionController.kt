package sidim.doma.undying.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    fun findBodyPartsInGraveyard(@RequestBody req: NewFindingBodyPartsInGraveyardReq): ResponseEntity<List<BodyPart>> {
        return ResponseEntity.ok(actionService.findBodyPartsInGraveyard(req.graveyardId, req.scholarId))
    }
}
