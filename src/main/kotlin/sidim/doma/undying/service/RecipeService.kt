package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.exceptionhandler.exception.RecipeException
import sidim.doma.undying.generated.tables.pojos.Recipes
import sidim.doma.undying.mapper.RecipeMapper
import sidim.doma.undying.model.Recipe
import sidim.doma.undying.repository.RecipeRepository

@Service
class RecipeService(private val recipeRepository: RecipeRepository, private val recipeMapper: RecipeMapper) {
    fun createRecipe(dto: NewRecipeDto): Recipes {
        checkExistsRecipeByName(dto.name)
        return recipeRepository.saveNewRecipe(dto)
    }

    fun getRecipeById(recipeId: Int): Recipes {
        return recipeRepository.findRecipeById(recipeId)
            ?: throw RecipeException("Recipe with id: $recipeId not found", HttpStatus.NOT_FOUND)
    }

    fun getFullRecipeById(recipeId: Int): Recipe {
        val fullRecipeRecord = recipeRepository.findFullRecipeById(recipeId)
        if (fullRecipeRecord.first == null) {
            throw RecipeException("Recipe with id: $recipeId not found", HttpStatus.NOT_FOUND)
        }
        return recipeMapper.fromRecipePojoToModel(fullRecipeRecord)
    }

    fun checkExistsRecipeById(recipeId: Int) {
        if (recipeRepository.isRecipeExistById(recipeId)) {
            throw RecipeException("Recipe with id: $recipeId already exists", HttpStatus.CONFLICT)
        }
    }

    fun checkExistsRecipeByName(recipeName: String) {
        if (recipeRepository.isRecipeExistByName(recipeName)) {
            throw RecipeException("Recipe with name: $recipeName already exists", HttpStatus.CONFLICT)
        }
    }
}