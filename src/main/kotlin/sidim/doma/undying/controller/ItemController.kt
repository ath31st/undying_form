package sidim.doma.undying.controller

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sidim.doma.undying.dto.PageDto
import sidim.doma.undying.dto.item.NewItemDto
import sidim.doma.undying.generated.tables.pojos.Items
import sidim.doma.undying.service.storage.ItemService

@RestController
@Validated
@RequestMapping("/api/v1/items")
class ItemController(
    private val itemService: ItemService,
) {
    @PostMapping("/new_item")
    fun createItem(@RequestBody dto: NewItemDto): ResponseEntity<HttpStatus> {
        itemService.createItem(dto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/all_with_search")
    fun getAllItems(
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
    ): ResponseEntity<PageDto<Items>> {
        val directionEnum = if (direction == "asc") Sort.Direction.ASC else Sort.Direction.DESC
        val sort = Sort.by(directionEnum, key)
        val req = PageRequest.of(pageNumber - 1, size, sort)

        val itemPage: PageDto<Items> = itemService.getAllItemsWithSearch(req, searchName)
        return ResponseEntity(itemPage, HttpStatus.OK)
    }
}
