package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sidim.doma.undying.dto.user.UserInfoDto
import sidim.doma.undying.dto.user.UserRegDto
import sidim.doma.undying.exceptionhandler.exception.UserException
import sidim.doma.undying.generated.tables.pojos.Users
import sidim.doma.undying.repository.UserRepository
import sidim.doma.undying.service.scholar.ScholarService
import sidim.doma.undying.service.scholar.TraitService
import sidim.doma.undying.util.Role

@Service
class UserService(
    private val userRepository: UserRepository,
    private val scholarService: ScholarService,
    private val traitService: TraitService,
    private val recipeBookService: RecipeBookService,
) {
    @Transactional
    fun registerUser(dto: UserRegDto): Users {
        if (userRepository.isUserExistByUsername(dto.username)) {
            throw UserException("Username ${dto.username} already used", HttpStatus.CONFLICT)
        }
        val scholar = scholarService.createScholar()
        traitService.generateAndSaveRandomSetTraits(scholar.scholarId ?: 0)
        val recipeBook = recipeBookService.createEmptyRecipeBook()

        return userRepository.saveUser(dto, scholar.scholarId ?: 0, recipeBook.recipeBookId ?: 0)
    }

    fun getUserInfo(id: Long): UserInfoDto {
        val u = userRepository.getUserById(id)
        if (u != null) {
            return UserInfoDto(
                u.userId ?: 0,
                Role.getRoleByIndex(u.role ?: 0).value,
                u.username.let { it ?: "" },
                u.email.let { it ?: "" },
            )
        } else {
            throw UserException("User with id $id not found", HttpStatus.NOT_FOUND)
        }
    }

    fun updateUserActiveStatus(id: Long, newStatus: Boolean) {
        checkIsUserExistById(id)
        userRepository.updateActiveStatus(id, newStatus)
    }

    fun updateUserBlockedStatus(id: Long, newStatus: Boolean) {
        checkIsUserExistById(id)
        userRepository.updateBlockedStatus(id, newStatus)
    }

    private fun checkIsUserExistById(id: Long) {
        if (!userRepository.existsUserWithId(id)) {
            throw UserException("User with id $id not found", HttpStatus.NOT_FOUND)
        }
    }
}
