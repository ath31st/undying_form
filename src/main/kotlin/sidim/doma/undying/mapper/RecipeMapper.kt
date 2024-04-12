package sidim.doma.undying.mapper

import org.jooq.Record1
import org.jooq.Result
import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.generated.tables.records.ItemsRecord
import sidim.doma.undying.generated.tables.records.RecipesRecord
import sidim.doma.undying.model.Recipe

@Component
class RecipeMapper() {
    fun fromRecipePojoToModel(pair: Pair<RecipesRecord, Result<Record1<ItemsRecord>>>): Recipe {
        val r = pair.first
        val items = pair.second

        return Recipe(
            recipeId = r.recipeId ?: 0,
            name = r.name ?: "",
            description = r.description ?: "",
            rarity = r.rarity ?: 0,
            ingredients = items.map { it.into(Items::class.java) }
        )
    }
}