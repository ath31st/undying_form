package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.jooq.Table
import org.jooq.TableField
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import sidim.doma.undying.dto.PageDto

@Component
class CommonRepositoryMethods {
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

    fun isRecordExistByStringField(
        dslContext: DSLContext,
        table: Table<*>,
        nameField: TableField<*, String?>,
        value: String
    ): Boolean {
        return dslContext.selectCount()
            .from(table)
            .where(nameField.eq(value.trim()))
            .fetchOneInto(Int::class.java) == 1
    }
}