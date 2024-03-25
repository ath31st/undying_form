package sidim.doma.undying.exceptionhandler.exception

import org.springframework.http.HttpStatus

class ItemException(message: String? = null, val status: HttpStatus, cause: Throwable? = null) :
    RuntimeException(message, cause)
