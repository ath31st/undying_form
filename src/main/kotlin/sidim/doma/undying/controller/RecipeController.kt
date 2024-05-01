package sidim.doma.undying.controller

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.recipe.NewRecipeDto
import sidim.doma.undying.generated.tables.pojos.Recipes
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

    @GetMapping("/all_with_search")
    fun getAllRecipes(
        @RequestParam @Pattern(
            regexp = "[А-Яа-я0-9- ]{1,100}",
            message = "The name for the search must be from 1 to 100 characters"
        ) searchName: String?,
        @RequestParam(defaultValue = "0") @Min(
            value = 1,
            message = "Page number must not be less than one"
        ) pageNumber: Int,
        @RequestParam(defaultValue = "10") @Min(
            value = 1,
            message = "Page size must not be less than one"
        ) size: Int,
        @RequestParam(defaultValue = "asc") @Pattern(
            regexp = "(asc|desc)",
            message = "The sorting direction is indicated by asc or desc"
        ) direction: String,
        @RequestParam(defaultValue = "item_id") @Pattern(
            regexp = "[a-z][a-zA-Z0-9_]{1,100}",
            message = "The key format is incorrect, or the requested list does not have such a key"
        ) key: String,
    ): ResponseEntity<PageDto<Recipes>> {
        val directionEnum = if (direction == "asc") Sort.Direction.ASC else Sort.Direction.DESC
        val sort = Sort.by(directionEnum, key)
        val req = PageRequest.of(pageNumber - 1, size, sort)

        val recipePage: PageDto<Recipes> = recipeService.getAllRecipesWithSearch(req, searchName)
        return ResponseEntity(recipePage, HttpStatus.OK)
    }
}