package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.RecipeException
import sidim.doma.undying.mapper.RecipeMapper
import sidim.doma.undying.repository.RecipeRepository

@Service
class RecipeService(private val recipeRepository: RecipeRepository, private val recipeMapper: RecipeMapper) {
    fun checkExistsRecipeById(recipeId: Int) {
        if (recipeRepository.isRecipeExistById(recipeId)) {
            throw RecipeException("Recipe with id: $recipeId already exists", HttpStatus.CONFLICT)
        }
    }
}