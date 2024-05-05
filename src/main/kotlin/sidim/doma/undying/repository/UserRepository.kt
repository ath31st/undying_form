package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.user.UserRegDto
import sidim.doma.undying.generated.tables.pojos.Users
import sidim.doma.undying.generated.tables.references.USERS
import sidim.doma.undying.util.Role
import java.time.LocalDate

@Repository
class UserRepository(
    private val dslContext: DSLContext,
    private val commonRepositoryMethods: CommonRepositoryMethods,
) {
    private val u = USERS

    fun isUserExistByUsername(username: String): Boolean {
        return commonRepositoryMethods.isRecordExistByStringField(dslContext, u, u.USERNAME, username)
    }

    fun existsUserWithId(id: Long): Boolean {
        return dslContext.selectCount()
            .from(u)
            .where(u.USER_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }

    fun saveUser(dto: UserRegDto, scholarId: Long, recipeBookId: Long): Users {
        val r = dslContext.newRecord(u)
        r.username = dto.username
        r.email = dto.email
        r.registerDate = LocalDate.now()
        r.isActive = true
        r.isNotBlocked = true
        r.role = Role.ROLE_GAMER.ordinal
        r.scholarId = scholarId
        r.recipeBookId = recipeBookId

        r.store()
        return r.into(Users::class.java)
    }

    fun getUserByUsername(username: String): Users? {
        return dslContext.select(u)
            .where(u.USERNAME.eq(username))
            .fetchOneInto(Users::class.java)
    }

    fun getUserById(id: Long): Users? {
        return dslContext.select()
            .from(u)
            .where(u.USER_ID.eq(id))
            .fetchOneInto(Users::class.java)
    }

    fun updateActiveStatus(id: Long, newStatus: Boolean) {
        dslContext.update(u)
            .set(u.IS_ACTIVE, newStatus)
            .where(u.USER_ID.eq(id))
            .execute()
    }

    fun updateBlockedStatus(id: Long, newStatus: Boolean) {
        dslContext.update(u)
            .set(u.IS_NOT_BLOCKED, newStatus)
            .where(u.USER_ID.eq(id))
            .execute()
    }
}
