package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.repository.monster.MonsterRepository

@Service
class MonsterService(
    private val monsterRepository: MonsterRepository,
    private val setBodyPartsService: SetBodyPartsService
) {

    fun createMonster(): Monsters {
        val setsBodyParts = setBodyPartsService.createEmptySet()
        return monsterRepository.saveNewMonster(setsBodyParts.setBodyPartsId ?: 0)
    }
}
