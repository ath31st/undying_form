package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.Record2
import org.jooq.Result
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.generated.tables.pojos.Recipes
import sidim.doma.undying.generated.tables.records.ItemsRecord
import sidim.doma.undying.generated.tables.records.RecipesRecord
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

    fun findRecipeById(id: Int): Recipes? {
        return dslContext.select(r)
            .from(r)
            .where(r.RECIPE_ID.eq(id))
            .fetchOneInto(Recipes::class.java)
    }

    fun findFullRecipeById(id: Int): Result<Record2<RecipesRecord, ItemsRecord>> {
        return dslContext.select(r, i)
            .from(r)
            .join(i).on(i.ITEM_ID.eq(r.items.ITEM_ID))
            .where(r.RECIPE_ID.eq(id))
            .fetch()
    }

    fun isRecipeExistByName(name: String): Boolean {
        return dslContext.selectCount()
            .from(r)
            .where(r.NAME.eq(name.trim()))
            .fetchOneInto(Int::class.java) == 1
    }

    fun isRecipeExistById(id: Int): Boolean {
        return dslContext.selectCount()
            .from(r)
            .where(r.RECIPE_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }
}
