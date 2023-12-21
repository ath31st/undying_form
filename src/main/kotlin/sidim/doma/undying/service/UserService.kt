package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.UserInfoDto
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.exception.UserException
import sidim.doma.undying.generated.tables.pojos.Users
import sidim.doma.undying.repository.UserRepository
import sidim.doma.undying.util.Role

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(dto: UserRegDto): Users {
        if (userRepository.isUserExistByUsername(dto.username)) {
            throw UserException("Username ${dto.username} already used", HttpStatus.CONFLICT)
        }
        return userRepository.createUser(dto)
    }

    fun getUserInfo(id: Int): UserInfoDto {
        val u = userRepository.getUserById(id)
        if (u != null) {
            return UserInfoDto(u.id!!, Role.getRoleByIndex(u.role!!).value, u.username!!, u.name!!)
        } else {
            throw UserException("User with id $id not found", HttpStatus.NOT_FOUND)
        }
    }
}