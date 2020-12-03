package geoiss.config.tracing

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class RequestTracingFilter(
    @Value("\${spring.application.name}") val applicationName: String,
    val requestTrace: RequestTrace
) : OncePerRequestFilter() {

    companion object {
        const val TRACE_HEADER = "X-Correlation-ID"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            requestTrace.id = getRequestTraceId(request)
            response.setHeader(TRACE_HEADER, requestTrace.id)
            MDC.put("traceId", requestTrace.id)
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove("traceId")
        }
    }

    private fun getRequestTraceId(request: HttpServletRequest): String =
        if (StringUtils.isNotEmpty(request.getHeader(TRACE_HEADER))) request.getHeader(TRACE_HEADER)
        else "$applicationName-${UUID.randomUUID()}"
}
