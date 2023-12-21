package sidim.doma.undying.repository

import java.time.LocalDateTime
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.Role
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.generated.tables.pojos.Users
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

    fun createUser(dto: UserRegDto): Users {
        val record = dslContext.newRecord(USERS, Users().apply {
            username = dto.username
            name = dto.name
            registerDate = LocalDateTime.now().toString()
            isActive = true
            isNotBlocked = true
            role = Role.ROLE_ADMINISTRATOR.ordinal
        })
        record.store()
        return record.into(Users::class.java)
    }
}