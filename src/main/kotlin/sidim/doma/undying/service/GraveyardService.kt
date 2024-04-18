package sidim.doma.undying.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.exceptionhandler.exception.GraveyardException
import sidim.doma.undying.generated.tables.pojos.Graveyards
import sidim.doma.undying.repository.location.GraveyardRepository

@Service
class GraveyardService(
    private val graveyardRepository: GraveyardRepository,
) {
    fun getGraveyardById(graveyardId: Int): Graveyards {
        return graveyardRepository.findGraveyardById(graveyardId) ?: throw GraveyardException(
            "Graveyard with id: $graveyardId not found.",
            HttpStatus.NOT_FOUND
        )
    }

    fun checkExistsGraveyardWithId(graveyardId: Int) {
        if (!graveyardRepository.existsGraveyardWithId(graveyardId)) {
            throw GraveyardException("Graveyard with id: $graveyardId not found.", HttpStatus.NOT_FOUND)
        }
    }
}
