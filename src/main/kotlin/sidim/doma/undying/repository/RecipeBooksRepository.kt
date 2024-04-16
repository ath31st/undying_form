package sidim.doma.undying.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.pojos.RecipeBooks
import sidim.doma.undying.generated.tables.references.RECIPE_BOOKS
import sidim.doma.undying.generated.tables.references.RECIPE_BOOK_RECIPES
import sidim.doma.undying.generated.tables.references.SCHOLARS
import sidim.doma.undying.generated.tables.references.USERS

@Repository
class RecipeBooksRepository(private val dslContext: DSLContext) {
    private val rb = RECIPE_BOOKS
    private val r = RECIPE_BOOK_RECIPES
    private val u = USERS
    private val sc = SCHOLARS
    private val rbr = RECIPE_BOOK_RECIPES

    fun saveEmptyRecipeBook(title: String): RecipeBooks {
        val r = dslContext.newRecord(rb)
        r.title = title
        r.previousOwnerFirstName = ""
        r.previousOwnerLastName = ""

        r.store()
        return r.into(RecipeBooks::class.java)
    }

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

    fun findRecipeBookByUserId(userId: Long): Pair<RecipeBooks?, List<Int>> {
        val recipeBook = dslContext.select(rb)
            .from(rb)
            .join(rb.users)
            .where(rb.users.USER_ID.eq(userId))
            .fetchOneInto(RecipeBooks::class.java)

        val recipeIds = mutableListOf<Int>()
        if (recipeBook != null) {
            dslContext.select(r.RECIPE_ID)
                .from(r)
                .join(rb.recipes)
                .where(r.RECIPE_BOOK_ID.eq(recipeBook.recipeBookId))
                .fetch().map { it.value1()?.let { it1 -> recipeIds.add(it1) } }
        }

        return Pair(recipeBook, recipeIds)
    }

    fun setFirstLastNameCurrentScholarByUserId(userId: Long, firstName: String, lastName: String) {
        dslContext.update(rb)
            .set(rb.PREVIOUS_OWNER_FIRST_NAME, firstName)
            .set(rb.PREVIOUS_OWNER_LAST_NAME, lastName)
            .where(
                rb.RECIPE_BOOK_ID.eq(
                    dslContext.select(u.RECIPE_BOOK_ID)
                        .from(u)
                        .where(u.USER_ID.eq(userId))
                        .fetchOneInto(Long::class.java)
                )
            ).execute()
    }

    fun getFirstLastNamePreviousScholarByUserId(userId: Long): Pair<String, String> {
        return dslContext.select(rb.PREVIOUS_OWNER_FIRST_NAME, rb.PREVIOUS_OWNER_LAST_NAME)
            .from(rb)
            .join(u).on(u.RECIPE_BOOK_ID.eq(rb.RECIPE_BOOK_ID))
            .where(u.USER_ID.eq(userId))
            .fetchOne()
            ?.let { Pair(it.value1() ?: "", it.value2() ?: "") } ?: Pair("", "")
    }
}