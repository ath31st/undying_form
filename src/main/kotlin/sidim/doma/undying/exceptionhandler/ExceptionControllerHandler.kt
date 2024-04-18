package sidim.doma.undying.exceptionhandler

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.jooq.exception.IntegrityConstraintViolationException
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import sidim.doma.undying.exceptionhandler.exception.*
import java.util.stream.Collectors


@ControllerAdvice
class ExceptionControllerHandler {

    @ExceptionHandler
    fun handleUserException(ex: UserException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleSpecializationException(ex: SpecializationException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleDistrictException(ex: DistrictException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleHideoutException(ex: HideoutException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleEducationException(ex: EducationException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleTraitException(ex: TraitException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleNamingException(ex: NamingException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleBodyPartException(ex: BodyPartException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleBodyPartsTemplateException(ex: BodyPartTemplateException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleSetBodyPartsException(ex: SetBodyPartsException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleMonsterException(ex: MonsterException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleStorageException(ex: StorageException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleScholarException(ex: ScholarException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleGraveyardException(ex: GraveyardException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleSocialClassException(ex: SocialClassException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handlePlayerActionException(ex: PlayerActionException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    protected fun handleValidException(e: ConstraintViolationException): ResponseEntity<ErrorMessageModel> {
        val errorString = e.constraintViolations.stream()
            .map { obj: ConstraintViolation<*> -> obj.message }
            .collect(Collectors.joining(", "))
        val errorMessage = ErrorMessageModel(message = errorString, status = HttpStatus.BAD_REQUEST.value())
        return ResponseEntity<ErrorMessageModel>(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    protected fun handleValidException(e: IntegrityConstraintViolationException): ResponseEntity<ErrorMessageModel> {
        val errorMessage =
            ErrorMessageModel(message = e.message?.substringAfter("ERROR: "), status = HttpStatus.BAD_REQUEST.value())
        return ResponseEntity<ErrorMessageModel>(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    protected fun handleException(e: PropertyReferenceException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(message = e.message, status = HttpStatus.BAD_REQUEST.value())
        return ResponseEntity<ErrorMessageModel>(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleItemException(ex: ItemException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleRecipeBookException(ex: RecipeBookException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleRecipeException(ex: RecipeException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }

    @ExceptionHandler
    fun handleCityException(ex: CityException):
            ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ex.status.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }
}
