package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.user.UserInfoDto
import sidim.doma.undying.dto.user.UserRegDto
import sidim.doma.undying.service.UserService

@RestController
@Validated
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody dto: UserRegDto): ResponseEntity<HttpStatus> {
        userService.registerUser(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/info/{id}")
    fun userInfo(@PathVariable id: Long): ResponseEntity<UserInfoDto> {
        return ResponseEntity.ok(userService.getUserInfo(id))
    }
}
