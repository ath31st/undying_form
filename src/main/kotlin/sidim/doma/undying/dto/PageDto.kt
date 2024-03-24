package sidim.doma.undying.dto

data class PageDto<T>(
    val content: List<T>,
    val totalElements: Int,
    val totalPages: Int,
    val currentNumberPage: Int
)