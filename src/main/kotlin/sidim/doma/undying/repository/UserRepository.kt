package sidim.doma.undying.repository

import java.time.LocalDate
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.user.UserRegDto
import sidim.doma.undying.generated.tables.pojos.Users
import sidim.doma.undying.generated.tables.references.USERS
import sidim.doma.undying.util.Role

@Repository
class UserRepository(private val dslContext: DSLContext) {

    fun isUserExistByUsername(username: String): Boolean {
        return dslContext.selectCount()
            .from(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOneInto(Int::class.java) == 1
    }

    fun isUserExistById(id: Long): Boolean {
        return dslContext.selectCount()
            .from(USERS)
            .where(USERS.USER_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }

    fun createUser(dto: UserRegDto): Users {
        val r = dslContext.newRecord(USERS)
        r.username = dto.username
        r.email = dto.email
        r.registerDate = LocalDate.now()
        r.isActive = true
        r.isNotBlocked = true
        r.role = Role.ROLE_GAMER.ordinal
        r.scientistId = null

        r.store()
        return r.into(Users::class.java)
    }

    fun getUserByUsername(username: String): Users? {
        return dslContext.select(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOneInto(Users::class.java)
    }

    fun getUserById(id: Long): Users? {
        return dslContext.select()
            .from(USERS)
            .where(USERS.USER_ID.eq(id))
            .fetchOneInto(Users::class.java)
    }

    fun updateActiveStatus(id: Long, newStatus: Boolean) {
        dslContext.update(USERS)
            .set(USERS.IS_ACTIVE, newStatus)
            .where(USERS.USER_ID.eq(id))
            .execute()
    }

    fun updateBlockedStatus(id: Long, newStatus: Boolean) {
        dslContext.update(USERS)
            .set(USERS.IS_NOT_BLOCKED, newStatus)
            .where(USERS.USER_ID.eq(id))
            .execute()
    }
}
