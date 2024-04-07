package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.records.MonstersRecord
import sidim.doma.undying.generated.tables.records.SetsBodyPartsRecord
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.model.Monster
import sidim.doma.undying.model.SetBodyParts

@Component
class MonsterMapper {
    private val m = MONSTERS

    fun fromMonsterRecToModel(r1: MonstersRecord, r2: SetsBodyPartsRecord, setBodyParts: SetBodyParts): Monster {
        return Monster(
            monsterId = r1[m.MONSTER_ID] ?: 0,
            invisibility = r1[m.INVISIBILITY] ?: 0,
            strength = r1[m.STRENGTH] ?: 0,
            agility = r1[m.AGILITY] ?: 0,
            endurance = r1[m.ENDURANCE] ?: 0,
            stability = r1[m.STABILITY] ?: 0,
            setBodyParts = setBodyParts,
        )
    }
}
