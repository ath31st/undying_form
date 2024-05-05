package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.Record1
import org.jooq.Result
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.generated.tables.pojos.Recipes
import sidim.doma.undying.generated.tables.records.ItemsRecord
import sidim.doma.undying.generated.tables.records.RecipeItemsRecord
import sidim.doma.undying.generated.tables.records.RecipesRecord
import sidim.doma.undying.generated.tables.references.ITEMS
import sidim.doma.undying.generated.tables.references.RECIPES
import sidim.doma.undying.generated.tables.references.RECIPE_ITEMS

@Repository
class RecipeRepository(
    private val dslContext: DSLContext,
    private val commonRepositoryMethods: CommonRepositoryMethods,
) {
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

    fun findFullRecipeById(id: Int): Triple<
            RecipesRecord?,
            Result<Record1<ItemsRecord>>,
            Result<Record1<RecipeItemsRecord>>> {
        val recipeRecord = dslContext.selectFrom(r)
            .where(r.RECIPE_ID.eq(id))
            .fetchOne()

        val itemRecords = dslContext.select(i)
            .from(i)
            .join(ri).on(ri.ITEM_ID.eq(i.ITEM_ID))
            .where(ri.RECIPE_ID.eq(id))
            .fetch()

        val recipeItemRecords = dslContext.select(ri)
            .from(ri)
            .where(ri.RECIPE_ID.eq(id))
            .fetch()

        return Triple(recipeRecord, itemRecords, recipeItemRecords)
    }

    fun isRecipeExistByName(name: String): Boolean {
        return commonRepositoryMethods.isRecordExistByStringField(dslContext, r, r.NAME, name)
    }

    fun isRecipeExistById(id: Int): Boolean {
        return dslContext.selectCount()
            .from(r)
            .where(r.RECIPE_ID.eq(id))
            .fetchOneInto(Int::class.java) == 1
    }

    fun getRecipesWithPaginationAndSorting(req: PageRequest, name: String?): PageDto<Recipes> {
        val offset = req.offset

        val selectCondition = if (name != null) r.NAME.likeIgnoreCase("%${name.trim()}%") else null

        val totalElements = dslContext.selectCount()
            .from(r)
            .apply { if (selectCondition != null) where(selectCondition) }
            .fetchOneInto(Int::class.java) ?: 0

        val fieldName = req.sort.get().toList().firstOrNull()?.property?.let { r.field(it) } ?: r.RECIPE_ID
        val sortField =
            if (req.sort.get().anyMatch { it.direction == Sort.Direction.ASC }) fieldName.asc() else fieldName.desc()

        val recipes = dslContext.selectFrom(r)
            .apply { if (selectCondition != null) where(selectCondition) }
            .orderBy(sortField)
            .limit(req.pageSize)
            .offset(offset)
            .fetchInto(Recipes::class.java)

        return commonRepositoryMethods.createPageDto(req, totalElements, recipes)
    }
}
