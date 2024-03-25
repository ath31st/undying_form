package sidim.doma.undying.controller

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
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PageDto<Items>> {
        val itemsPage: PageDto<Items> = itemService.getAllItems(pageNumber, size)
        return ResponseEntity(itemsPage, HttpStatus.OK)
    }
}