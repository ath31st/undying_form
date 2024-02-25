package sidim.doma.undying.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.model.Monster
import sidim.doma.undying.service.monster.MonsterService

@RestController
@Validated
@RequestMapping("/api/v1/monsters")
class MonsterController(
    private val monsterService: MonsterService,
) {
    @GetMapping("/monster_info_by_scholar_id/{id}")
    fun getMonsterInfoByScholarId(@PathVariable id: Long): ResponseEntity<Monster> {
        return ResponseEntity.ok(monsterService.getMonsterInfoByScholarId(id))
    }
}
