package sidim.doma.undying.controller

import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.service.RecipeBookService

@RestController
class RecipeBookController(val recipeBookService: RecipeBookService) {

}