package sidim.doma.undying.util

import org.springframework.http.HttpStatus
import sidim.doma.undying.exception.UserException

enum class Role(val value: String) {
    ROLE_DEFAULT("Default"),
    ROLE_ADMINISTRATOR("Administrator"),
    ROLE_GAMER("Gamer"),
    ROLE_DEVELOPER("Developer");

    companion object {
        fun getRoleByValue(value: String): Role {
            return entries.find { it.value == value }
                ?: throw UserException("Role with value: $value not found", HttpStatus.BAD_REQUEST)
        }

        fun getRoleByIndex(index: Int): Role {
            return entries[index]
        }
    }
}