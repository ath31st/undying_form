package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.repository.monster.MonsterRepository

@Service
class MonsterService(private val monsterRepository: MonsterRepository) {

    fun createMonster(scholarId: Long, setBodyPartsId: Long): Monsters {
        return monsterRepository.createMonster(scholarId, setBodyPartsId)
    }
}
