package sidim.doma.undying.exceptionhandler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import sidim.doma.undying.exceptionhandler.exception.DistrictException
import sidim.doma.undying.exceptionhandler.exception.EducationException
import sidim.doma.undying.exceptionhandler.exception.HideoutException
import sidim.doma.undying.exceptionhandler.exception.SpecializationException
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
}
