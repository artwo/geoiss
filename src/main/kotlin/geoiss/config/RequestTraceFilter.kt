package geoiss.config

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class RequestTraceFilter @Autowired constructor(
    @Value("\${spring.application.name}") val applicationName: String,
    val requestTrace: RequestTrace
) : OncePerRequestFilter() {

    companion object {
        private const val TRACE_HEADER = "X-Correlation-ID"
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

@Configuration
class RequestTraceBeanProvider {

    @Bean
    @RequestScope
    fun requestTrace(): RequestTrace = RequestTrace()
}

open class RequestTrace(var id: String? = null)
