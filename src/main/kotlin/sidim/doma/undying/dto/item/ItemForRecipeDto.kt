package sidim.doma.undying.dto.item

data class ItemForRecipeDto(
    val itemId: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val quantity: Int,
)

