package sidim.doma.undying.exception

import org.springframework.http.HttpStatus

class UserException(message: String? = null, val status: HttpStatus, cause: Throwable? = null) :
    RuntimeException(message, cause)