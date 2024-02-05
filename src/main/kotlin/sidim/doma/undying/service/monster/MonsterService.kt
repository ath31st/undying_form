package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.MonsterException
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

    fun getMonsterInfoByScholarId(scholarId: Long): Monsters {
        return monsterRepository.findMonsterByScholarId(scholarId)
            ?: throw MonsterException("Monster for scholar id: $scholarId not found", HttpStatus.NOT_FOUND)
    }
}
