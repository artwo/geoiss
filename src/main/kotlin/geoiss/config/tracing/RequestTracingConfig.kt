package geoiss.config.tracing

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.RequestScope

@Configuration
class RequestTraceBeanProvider {

    @Bean
    @RequestScope
    fun requestTrace(): RequestTrace = RequestTrace()
}

open class RequestTrace(var id: String? = null)
