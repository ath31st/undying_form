package sidim.doma.undying.service

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.UserRegDto

@Service
class UserService {
    fun registerUser(dto: UserRegDto) {
        println("woop")
    }
}