package sidim.doma.undying.dto.recipe

import sidim.doma.undying.dto.item.ItemForNewRecipeDto

data class NewRecipeDto(
    val name: String,
    val description: String,
    val rarity: Int,
    val items: List<ItemForNewRecipeDto>,
)
