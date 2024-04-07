package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.MonsterException
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.mapper.MonsterMapper
import sidim.doma.undying.model.Monster
import sidim.doma.undying.repository.monster.MonsterRepository

@Service
class MonsterService(
    private val monsterRepository: MonsterRepository,
    private val setBodyPartsService: SetBodyPartsService,
    private val monsterMapper: MonsterMapper,
) {
    fun createMonster(): Monsters {
        val setsBodyParts = setBodyPartsService.createEmptySet()
        return monsterRepository.saveNewMonster(setsBodyParts.setBodyPartsId ?: 0)
    }

    fun getMonsterInfoByScholarId(scholarId: Long): Monster {
        val monsterId = monsterRepository.findMonsterIdByScholarId(scholarId)
            ?: throw MonsterException("Monster for scholar id: $scholarId not found", HttpStatus.NOT_FOUND)

        val setBodyParts = setBodyPartsService.getSetBodyPartsByMonsterId(monsterId)

        val record2 = monsterRepository.findMonsterByScholarId(scholarId)
            ?: throw MonsterException("Monster for scholar id: $scholarId not found", HttpStatus.NOT_FOUND)

        return monsterMapper.fromMonsterRecToModel(record2.value1(), record2.value2(), setBodyParts)
    }
}
