package sidim.doma.undying.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.model.Recipe
import sidim.doma.undying.service.RecipeService

@RestController
@Validated
@RequestMapping("/api/v1/recipes")
class RecipeController(private val recipeService: RecipeService) {
    @PostMapping("/new_recipe")
    fun createRecipe(@RequestBody dto: NewRecipeDto): ResponseEntity<HttpStatus> {
        recipeService.createRecipe(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/{recipeId}")
    fun getFullRecipeById(@PathVariable recipeId: Int): ResponseEntity<Recipe> {
        return ResponseEntity.ok(recipeService.getFullRecipeById(recipeId))
    }
}