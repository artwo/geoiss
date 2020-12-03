package geoiss.config

import org.springframework.stereotype.Component
import org.springframework.web.filter.CommonsRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

/**
 * Simple request logging filter that writes the requests information to the logs.
 */
@Component
class RequestLoggingFilter : CommonsRequestLoggingFilter() {

    init {
        isIncludeQueryString = true
        isIncludePayload = true
        isIncludeHeaders = false
        maxPayloadLength = 10000
    }

    override fun shouldLog(request: HttpServletRequest): Boolean = true

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        // Do nothing
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }
}
