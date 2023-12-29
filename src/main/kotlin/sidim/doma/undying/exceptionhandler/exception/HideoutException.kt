package sidim.doma.undying.exceptionhandler.exception

import org.springframework.http.HttpStatus

class HideoutException(message: String? = null, val status: HttpStatus, cause: Throwable? = null) :
    RuntimeException(message, cause)
