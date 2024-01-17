package sidim.doma.undying.exceptionhandler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import sidim.doma.undying.exceptionhandler.exception.BodyPartException
import sidim.doma.undying.exceptionhandler.exception.BodyPartTemplateException
import sidim.doma.undying.exceptionhandler.exception.DistrictException
import sidim.doma.undying.exceptionhandler.exception.EducationException
import sidim.doma.undying.exceptionhandler.exception.HideoutException
import sidim.doma.undying.exceptionhandler.exception.MonsterException
import sidim.doma.undying.exceptionhandler.exception.NamingException
import sidim.doma.undying.exceptionhandler.exception.SetBodyPartsException
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
}
