package sidim.doma.undying.exceptionhandler

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import java.time.LocalDateTime
import java.util.stream.Collectors
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import sidim.doma.undying.exceptionhandler.exception.BodyPartException
import sidim.doma.undying.exceptionhandler.exception.BodyPartTemplateException
import sidim.doma.undying.exceptionhandler.exception.DistrictException
import sidim.doma.undying.exceptionhandler.exception.EducationException
import sidim.doma.undying.exceptionhandler.exception.GraveyardException
import sidim.doma.undying.exceptionhandler.exception.HideoutException
import sidim.doma.undying.exceptionhandler.exception.MonsterException
import sidim.doma.undying.exceptionhandler.exception.NamingException
import sidim.doma.undying.exceptionhandler.exception.PlayerActionException
import sidim.doma.undying.exceptionhandler.exception.ScholarException
import sidim.doma.undying.exceptionhandler.exception.SetBodyPartsException
import sidim.doma.undying.exceptionhandler.exception.SocialClassException
import sidim.doma.undying.exceptionhandler.exception.SpecializationException
import sidim.doma.undying.exceptionhandler.exception.StorageException
import sidim.doma.undying.exceptionhandler.exception.TraitException
import sidim.doma.undying.exceptionhandler.exception.UserException


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
    protected fun handleException(e: PropertyReferenceException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(message = e.message, status = HttpStatus.BAD_REQUEST.value())
        return ResponseEntity<ErrorMessageModel>(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
