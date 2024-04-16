package sidim.doma.undying.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.model.RecipeBook
import sidim.doma.undying.service.RecipeBookService

@RestController
@Validated
@RequestMapping("/api/v1/recipe_books")
class RecipeBookController(val recipeBookService: RecipeBookService) {

    @GetMapping("/recipe_book/{userId}")
    fun getRecipeBookByUserId(@PathVariable userId: Long): ResponseEntity<RecipeBook> {
        return ResponseEntity.ok(recipeBookService.getRecipeBooksByUserId(userId))
    }
}