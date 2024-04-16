package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.RecipeBookException
import sidim.doma.undying.generated.tables.pojos.RecipeBooks
import sidim.doma.undying.mapper.RecipeBookMapper
import sidim.doma.undying.model.RecipeBook
import sidim.doma.undying.repository.RecipeBooksRepository

@Service
class RecipeBookService(
    private val recipeBooksRepository: RecipeBooksRepository,
    private val namingService: NamingService,
    private val recipeService: RecipeService,
    private val recipeBookMapper: RecipeBookMapper,
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

    fun getRecipeBooksByUserId(userId: Long): RecipeBook {
        val pair = recipeBooksRepository.findRecipeBookByUserId(userId)
        val rb = pair.first
            ?: throw RecipeBookException("No recipe book found for user id: $userId", HttpStatus.NOT_FOUND)
        val recipes = pair.second.map { recipeService.getFullRecipeById(it) }

        return recipeBookMapper.fromRecipeBookPojoToModel(rb, recipes)
    }

    fun setFirstLastNameCurrentScholarByUserId(userId: Long, firstName: String, lastName: String) {
        recipeBooksRepository.setFirstLastNameCurrentScholarByUserId(userId, firstName, lastName)
    }

    fun getFirstLastNamePreviousScholarByUserId(userId: Long): Pair<String, String> {
        return recipeBooksRepository.getFirstLastNamePreviousScholarByUserId(userId)
    }
}