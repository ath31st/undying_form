package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.RECIPE_BOOKS
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.USERS

@Repository
class RecipeBooksRepository(private val dslContext: DSLContext) {
    private val rb = RECIPE_BOOKS
    private val u = USERS
    private val sc = SCHOLARS

    fun isRecipeBookExistByScholarId(scholarId: Long): Boolean {
        return dslContext.selectCount()
            .from(rb)
            .join(u).on(u.RECIPE_BOOK_ID.eq(rb.RECIPE_BOOK_ID))
            .join(sc).on(sc.SCHOLAR_ID.eq(u.SCHOLAR_ID))
            .where(sc.SCHOLAR_ID.eq(scholarId))
            .fetchOneInto(Int::class.java) == 1
    }

    fun isRecipeBookExistByUserId(userId: Long): Boolean {
        return dslContext.selectCount()
            .from(rb)
            .join(u).on(u.RECIPE_BOOK_ID.eq(rb.RECIPE_BOOK_ID))
            .where(u.USER_ID.eq(userId))
            .fetchOneInto(Int::class.java) == 1
    }
}