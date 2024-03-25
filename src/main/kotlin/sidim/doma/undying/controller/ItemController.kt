package sidim.doma.undying.controller

import jakarta.validation.constraints.Min
import org.springframework.data.domain.PageRequest
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

    @GetMapping("/all")
    fun getAllItems(
        @RequestParam(defaultValue = "0") @Min(
            value = 1,
            message = "Page number must not be less than one"
        ) pageNumber: Int,
        @RequestParam(defaultValue = "10") @Min(
            value = 1,
            message = "Page size must not be less than one"
        ) size: Int
    ): ResponseEntity<PageDto<Items>> {
        val itemPage: PageDto<Items> = itemService.getAllItems(PageRequest.of(pageNumber - 1, size))
        return ResponseEntity(itemPage, HttpStatus.OK)
    }
}
