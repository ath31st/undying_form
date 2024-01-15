package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.BodyPartsTemplateException
import sidim.doma.undying.repository.monster.BodyPartsTemplateRepository

@Service
class BodyPartsTemplateService(private val bodyPartsTemplateRepository: BodyPartsTemplateRepository) {
    fun getRandomHandTemplateIdByGraveyardId(graveyardId: Int): Int {
        return bodyPartsTemplateRepository.getRandomHandTemplateIdByGraveyardId(graveyardId)
            ?: throw BodyPartsTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body parts templates",
                HttpStatus.NOT_FOUND
            )
    }

    fun getRandomLegTemplateIdByGraveyardId(graveyardId: Int): Int {
        return bodyPartsTemplateRepository.getRandomLegTemplateIdByGraveyardId(graveyardId)
            ?: throw BodyPartsTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body parts templates",
                HttpStatus.NOT_FOUND
            )
    }

    fun getRandomUpperBodyTemplateIdByGraveyardId(graveyardId: Int): Int {
        return bodyPartsTemplateRepository.getRandomUpperBodyTemplateIdByGraveyardId(graveyardId)
            ?: throw BodyPartsTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body parts templates",
                HttpStatus.NOT_FOUND
            )
    }

    fun getRandomHeadTemplateIdByGraveyardId(graveyardId: Int): Int {
        return bodyPartsTemplateRepository.getRandomHeadTemplateIdByGraveyardId(graveyardId)
            ?: throw BodyPartsTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body parts templates",
                HttpStatus.NOT_FOUND
            )
    }
}
