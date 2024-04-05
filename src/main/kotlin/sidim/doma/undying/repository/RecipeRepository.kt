package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.generated.tables.pojos.Recipes
import sidim.doma.undying.generated.tables.references.ITEMS
import sidim.doma.undying.generated.tables.references.RECIPES
import sidim.doma.undying.generated.tables.references.RECIPE_ITEMS

@Repository
class RecipeRepository(private val dslContext: DSLContext) {
    private val r = RECIPES
    private val ri = RECIPE_ITEMS
    private val i = ITEMS

    fun saveNewRecipe(dto: NewRecipeDto): Recipes {
        val recipeRecord = dslContext.newRecord(r)
        recipeRecord.name = dto.name
        recipeRecord.rarity = dto.rarity
        recipeRecord.description = dto.description

        recipeRecord.store()

        val recipeId = recipeRecord.recipeId
        for (i in dto.items) {
            val riRecord = dslContext.newRecord(ri)
            riRecord.recipeId = recipeId
            riRecord.itemId = i.itemId
            riRecord.quantity = i.quantity

            riRecord.store()
        }

        return recipeRecord.into(Recipes::class.java)
    }
}
