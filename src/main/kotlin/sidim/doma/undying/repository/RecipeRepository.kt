package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.Record1
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

    fun findFullRecipeById(id: Int): Pair<RecipesRecord?, Result<Record1<ItemsRecord>>> {
        val recipeRecord = dslContext.selectFrom(r)
            .where(r.RECIPE_ID.eq(id))
            .fetchOne()

        val itemRecords = dslContext.select(i)
            .from(i)
            .join(ri).on(ri.ITEM_ID.eq(i.ITEM_ID))
            .where(ri.RECIPE_ID.eq(id))
            .fetch()

        return Pair(recipeRecord, itemRecords)
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
