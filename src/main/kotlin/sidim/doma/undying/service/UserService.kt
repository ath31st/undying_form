package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.exception.UserException
import sidim.doma.undying.generated.tables.pojos.Users
import sidim.doma.undying.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(dto: UserRegDto): Users {
        if (userRepository.isUserExistByUsername(dto.username)) {
            throw UserException("username ${dto.username} already used")
        }
        return userRepository.createUser(dto)
    }
}