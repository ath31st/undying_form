package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.USERS

@Repository
class UserRepository(private val dslContext: DSLContext) {

    fun isUserExistByUsername(username: String): Boolean {
        return dslContext.selectCount()
            .from(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOneInto(Int::class.java) == 1
    }

    fun isUserExistByName(name: String): Boolean {
        return dslContext.selectCount()
            .from(USERS)
            .where(USERS.NAME.eq(name))
            .fetchOneInto(Int::class.java) == 1
    }
}