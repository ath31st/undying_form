package sidim.doma.undying.model

import sidim.doma.undying.dto.item.ItemForRecipeDto

data class Recipe(
    val recipeId: Int,
    val rarity: Int,
    val name: String,
    val description: String,
    val ingredients: List<ItemForRecipeDto>,
)