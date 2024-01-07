package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.generated.tables.references.MONSTERS

@Repository
class MonsterRepository(private val dslContext: DSLContext) {
    private val m = MONSTERS

    fun createMonster(scholarId: Long, setBodyPartsId: Long): Monsters {
        val r = dslContext.newRecord(m)
        r.scholarId = scholarId
        r.setBodyPartsId = setBodyPartsId
        r.invisibility = 0
        r.strength = 0
        r.agility = 0
        r.endurance = 0
        r.stability = 0

        r.store()
        return r.into(Monsters::class.java)
    }
}