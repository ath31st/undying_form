package sidim.doma.undying.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.service.UserService

@RestController
@Validated
@RequestMapping("/api/user")
class UserController(
    val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody dto: UserRegDto): ResponseEntity<Unit> {
        return ResponseEntity.ok(userService.registerUser(dto))
    }
}