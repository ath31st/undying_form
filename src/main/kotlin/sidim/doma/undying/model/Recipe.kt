package sidim.doma.undying.model

import sidim.doma.undying.generated.tables.pojos.Items

data class Recipe(
    val recipeId: Int,
    val rarity: Int,
    val name: String,
    val description: String,
    val ingredients: List<Items>,
)