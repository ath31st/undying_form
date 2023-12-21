package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.UserInfoDto
import sidim.doma.undying.dto.UserRegDto
import sidim.doma.undying.service.UserService

@RestController
@Validated
@RequestMapping("/api/users")
class UserController(
    val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody dto: UserRegDto): ResponseEntity<HttpStatus> {
        userService.registerUser(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/info/{id}")
    fun userInfo(@PathVariable id: Int): ResponseEntity<UserInfoDto> {
        return ResponseEntity.ok(userService.getUserInfo(id))
    }
}