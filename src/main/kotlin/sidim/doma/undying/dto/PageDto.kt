package sidim.doma.undying.dto

data class PageDto<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentNumberPage: Int
)