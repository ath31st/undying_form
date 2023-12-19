package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registerUser(dto: UserRegDto) {
        val regUser = userRepository.createUser(dto)
        println(regUser)
    }
}