package sidim.doma.undying.mapper

import org.jooq.Record1
import org.jooq.Result
import org.springframework.stereotype.Component
import sidim.doma.undying.dto.item.ItemForRecipeDto
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.generated.tables.records.ItemsRecord
import sidim.doma.undying.generated.tables.records.RecipeItemsRecord
import sidim.doma.undying.generated.tables.records.RecipesRecord
import sidim.doma.undying.model.Recipe

@Component
class RecipeMapper {
    fun fromRecipePojoToModel(
        triple: Triple<RecipesRecord?,
                Result<Record1<ItemsRecord>>,
                Result<Record1<RecipeItemsRecord>>>
    ): Recipe {
        val r = triple.first
        val items = triple.second
        val quantityItems = triple.third

        val ingredients = items.map { itemRecord ->
            val itemId = itemRecord.into(Items::class.java).itemId ?: 0
            val recipeItemRecord = quantityItems.find { it.value1().itemId == itemId }
            val quantity = recipeItemRecord?.value1()?.quantity ?: 0

            ItemForRecipeDto(
                itemId = itemId,
                name = itemRecord.into(Items::class.java).name ?: "",
                description = itemRecord.into(Items::class.java).description ?: "",
                rarity = itemRecord.into(Items::class.java).rarity ?: 0,
                quantity = quantity
            )
        }.toList()

        return Recipe(
            recipeId = r!!.recipeId ?: 0,
            name = r.name ?: "",
            description = r.description ?: "",
            rarity = r.rarity ?: 0,
            ingredients = ingredients
        )
    }
}