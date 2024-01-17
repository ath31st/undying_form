package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.BodyPartTemplateException
import sidim.doma.undying.repository.monster.BodyPartsTemplateRepository

@Service
class BodyPartTemplateService(private val bodyPartsTemplateRepository: BodyPartsTemplateRepository) {
    fun getRandomBodyPartTemplateIdByGraveyardId(graveyardId: Int): Int {
        val templateId = bodyPartsTemplateRepository.getRandomBodyPartTemplateIdByGraveyardId(graveyardId)
        checkExistsBodyPartTemplate(templateId, graveyardId)
        return templateId!!
    }

    private fun checkExistsBodyPartTemplate(templateId: Int?, graveyardId: Int) {
        if (templateId == null) {
            throw BodyPartTemplateException(
                "Graveyard with id: $graveyardId does not have compatible body part template",
                HttpStatus.NOT_FOUND
            )
        }
    }
}
