package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.BodyPartsTemplateException
import sidim.doma.undying.repository.monster.BodyPartsTemplateRepository

@Service
class BodyPartsTemplateService(private val bodyPartsTemplateRepository: BodyPartsTemplateRepository) {
    fun getRandomHandTemplateIdByGraveyardId(graveyardId: Int): Int {
        val handTemplateId = bodyPartsTemplateRepository.getRandomHandTemplateIdByGraveyardId(graveyardId)
        checkExistsBodyPartsTemplate(handTemplateId, graveyardId)
        return handTemplateId!!
    }

    fun getRandomLegTemplateIdByGraveyardId(graveyardId: Int): Int {
        val legTemplateId = bodyPartsTemplateRepository.getRandomLegTemplateIdByGraveyardId(graveyardId)
        checkExistsBodyPartsTemplate(legTemplateId, graveyardId)
        return legTemplateId!!
    }

    fun getRandomUpperBodyTemplateIdByGraveyardId(graveyardId: Int): Int {
        val upperBodyTemplateId = bodyPartsTemplateRepository.getRandomUpperBodyTemplateIdByGraveyardId(graveyardId)
        checkExistsBodyPartsTemplate(upperBodyTemplateId, graveyardId)
        return upperBodyTemplateId!!
    }

    fun getRandomHeadTemplateIdByGraveyardId(graveyardId: Int): Int {
        val headTemplateId = bodyPartsTemplateRepository.getRandomHeadTemplateIdByGraveyardId(graveyardId)
        checkExistsBodyPartsTemplate(headTemplateId, graveyardId)
        return headTemplateId!!
    }

    private fun checkExistsBodyPartsTemplate(templateId: Int?, graveyardId: Int) {
        if (templateId == null) {
            throw BodyPartsTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body parts templates",
                HttpStatus.NOT_FOUND
            )
        }
    }
}
