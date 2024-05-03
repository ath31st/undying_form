package sidim.doma.undying.repository

import org.springframework.data.domain.PageRequest
import sidim.doma.undying.dto.PageDto

object CommonRepositoryMethods {
    fun <T> createPageDto(req: PageRequest, totalElements: Int, content: List<T>): PageDto<T> {
        val totalPages = if (totalElements % req.pageSize == 0) {
            totalElements / req.pageSize
        } else {
            (totalElements / req.pageSize) + 1
        }

        return PageDto(
            content = content,
            totalElements = totalElements,
            totalPages = totalPages,
            currentNumberPage = req.pageNumber + 1
        )
    }
}