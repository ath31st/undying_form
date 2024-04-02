package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.RecipeBookException
import sidim.doma.undying.generated.tables.pojos.RecipeBooks
import sidim.doma.undying.repository.RecipeBooksRepository

@Service
class RecipeBookService(
    private val recipeBooksRepository: RecipeBooksRepository,
    private val namingService: NamingService
) {
    fun checkExistsRecipeBookByUserId(userId: Long) {
        if (recipeBooksRepository.isRecipeBookExistByUserId(userId)) {
            throw RecipeBookException("Recipe book for user with id: $userId already exists", HttpStatus.CONFLICT)
        }
    }

    fun createEmptyRecipeBook(): RecipeBooks {
        val title = namingService.generateRecipeBookTitle()
        return recipeBooksRepository.saveEmptyRecipeBook(title)
    }

    fun setFirstLastNameCurrentScholarByUserId(userId: Long, firstName: String, lastName: String) {
        recipeBooksRepository.setFirstLastNameCurrentScholarByUserId(userId, firstName, lastName)
    }

    fun getFirstLastNamePreviousScholarByUserId(userId: Long): Pair<String, String> {
        return recipeBooksRepository.getFirstLastNamePreviousScholarByUserId(userId)
    }
}