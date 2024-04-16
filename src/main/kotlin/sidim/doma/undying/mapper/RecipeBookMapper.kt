package sidim.doma.undying.mapper

import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.RecipeBooks
import sidim.doma.undying.model.Recipe
import sidim.doma.undying.model.RecipeBook

@Component
class RecipeBookMapper {
    fun fromRecipeBookPojoToModel(recipeBook: RecipeBooks, recipes: List<Recipe>): RecipeBook {
        return RecipeBook(
            recipeBook.recipeBookId ?: 0,
            recipeBook.title ?: "",
            recipeBook.previousOwnerFirstName ?: "",
            recipeBook.previousOwnerLastName ?: "",
            recipes
        )
    }
}