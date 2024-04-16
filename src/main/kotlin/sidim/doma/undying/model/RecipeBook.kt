package sidim.doma.undying.model

data class RecipeBook(
    val recipeBookId: Long,
    val title: String,
    val previousOwnerFirstName: String,
    val previousOwnerLastName: String,
    val list: List<Recipe>
)