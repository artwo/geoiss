package geoiss.config.tracing

import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.stereotype.Component

@Component
class HttpClientTraceInterceptor(
    private val requestTrace: RequestTrace
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = requestTrace.id?.let { traceId ->
            chain.request().newBuilder()
                .addHeader(RequestTracingFilter.TRACE_HEADER, traceId)
                .build()
        } ?: chain.request()
        return chain.proceed(request)
    }
}
