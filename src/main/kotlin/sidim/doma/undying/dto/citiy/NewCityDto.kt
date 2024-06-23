package sidim.doma.undying.dto.citiy

data class NewCityDto(
    val name: String,
    val population: Int,
    val description: String,
    val foundationYear: Int,
    val flagUrl: String,
)
