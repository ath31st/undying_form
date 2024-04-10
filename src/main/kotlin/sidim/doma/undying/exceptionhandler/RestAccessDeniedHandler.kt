package sidim.doma.undying.exceptionhandler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime


@Component
class RestAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class)
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.outputStream.println(
            "{ \"timestamp\": \"" + LocalDateTime.now() + "\"\n\t"
                    + "\"status\": \"" + HttpStatus.FORBIDDEN + "\"\n\t"
                    + "\"error\": \"" + accessDeniedException.message + "\" }"
        )
    }
}
