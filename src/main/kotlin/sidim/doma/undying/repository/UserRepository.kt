package sidim.doma.undying.repository

import java.time.LocalDateTime
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

    fun createUser(dto: UserRegDto): Users {
        val record = dslContext.newRecord(USERS, Users().apply {
            username = dto.username
            email = dto.email
            registerDate = LocalDateTime.now().toString()
            isActive = true
            isNotBlocked = true
            role = Role.ROLE_GAMER.ordinal
        })
        record.store()
        return record.into(Users::class.java)
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
}
