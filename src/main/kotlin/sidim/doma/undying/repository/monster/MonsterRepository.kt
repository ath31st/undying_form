package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.jooq.Record2
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.Monsters
import sidim.doma.undying.generated.tables.records.MonstersRecord
import sidim.doma.undying.generated.tables.records.SetsBodyPartsRecord
import sidim.doma.undying.generated.tables.references.BODY_PARTS
import sidim.doma.undying.generated.tables.references.BODY_PART_TEMPLATES
import sidim.doma.undying.generated.tables.references.MONSTERS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.SETS_BODY_PARTS

@Repository
class MonsterRepository(private val dslContext: DSLContext) {
    private val m = MONSTERS
    private val s = SCHOLARS
    private val sbp = SETS_BODY_PARTS
    private val bpt = BODY_PART_TEMPLATES
    private val bp = BODY_PARTS

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

    fun findMonsterIdByScholarId(scholarId: Long): Long? {
        return dslContext.select(m.MONSTER_ID)
            .from(m)
            .join(s).on(s.MONSTER_ID.eq(m.MONSTER_ID))
            .where(s.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Long::class.java)
    }

    fun findMonsterByScholarId(scholarId: Long): Record2<MonstersRecord, SetsBodyPartsRecord>? {
        return dslContext.select(m, sbp)
            .from(m)
            .join(s).on(s.MONSTER_ID.eq(m.MONSTER_ID))
            .join(sbp).on(sbp.SET_BODY_PARTS_ID.eq(m.SET_BODY_PARTS_ID))
            .where(s.SCHOLAR_ID.eq(scholarId))
            .fetchOne()
    }
}