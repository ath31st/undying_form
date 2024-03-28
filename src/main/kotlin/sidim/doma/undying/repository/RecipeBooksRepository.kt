package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.RECIPE_BOOKS
import sidim.doma.undying.generated.tables.references.SCHOLARS

@Repository
class RecipeBooksRepository(private val dslContext: DSLContext) {
    private val rb = RECIPE_BOOKS
    private val sc = SCHOLARS

//    fun isRecipeBookExistByScholarId(scholarId: Long): Boolean {
//        return dslContext.selectCount()
//            .from(rb)
//            .join(sc).on(sc.)
//            .where(sc.SCHOLAR_ID.eq(scholarId))
//            .fetchOneInto(Int::class.java) == 1
//    }
}