package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import sidim.doma.undying.Role
import sidim.doma.undying.exception.UserException
import sidim.doma.undying.repository.UserRepository

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.getUserByUsername(username)
        if (user != null) {
            return User.builder()
                .username(user.username)
                .roles(user.role?.let { Role.getRoleByIndex(it).name })
                .build()
        } else {
            throw UserException("User with $username not found", HttpStatus.NOT_FOUND)
        }
    }
}