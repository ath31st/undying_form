package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.RecipeBookException
import sidim.doma.undying.repository.RecipeBooksRepository

@Service
class RecipeBookService(private val recipeBooksRepository: RecipeBooksRepository) {
    fun checkExistsRecipeBookByUserId(userId: Long) {
        if (recipeBooksRepository.isRecipeBookExistByUserId(userId)) {
            throw RecipeBookException("Recipe book for user with id: $userId already exists", HttpStatus.CONFLICT)
        }
    }
}