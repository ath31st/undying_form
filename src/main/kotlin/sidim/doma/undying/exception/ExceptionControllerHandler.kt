package sidim.doma.undying.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

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
}
