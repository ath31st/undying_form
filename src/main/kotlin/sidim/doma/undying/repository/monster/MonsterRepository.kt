package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.generated.tables.references.SCHOLARS

@Repository
class MonsterRepository(private val dslContext: DSLContext) {
    private val m = MONSTERS
    private val s = SCHOLARS

    fun saveNewMonster(setBodyPartsId: Long): Monsters {
        val r = dslContext.newRecord(m)
        r.setBodyPartsId = setBodyPartsId
        r.invisibility = 0
        r.strength = 0
        r.agility = 0
        r.endurance = 0
        r.stability = 0

        r.store()
        return r.into(Monsters::class.java)
    }

    fun findMonsterById(monsterId: Long): Monsters? {
        return dslContext.select(m)
            .from(m)
            .where(m.MONSTER_ID.eq(monsterId))
            .fetchOneInto(Monsters::class.java)
    }

    fun findMonsterByScholarId(scholarId: Long): Monsters? {
        return dslContext.select(m)
            .from(m)
            .join(s).on(s.MONSTER_ID.eq(m.MONSTER_ID))
            .where(s.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Monsters::class.java)
    }
}