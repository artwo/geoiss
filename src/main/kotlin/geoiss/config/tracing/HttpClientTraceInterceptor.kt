package geoiss.config.tracing

import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.stereotype.Component

@Component
class HttpClientTraceInterceptor(
    private val requestTrace: RequestTrace
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = requestTrace.id?.let { traceId ->
            originalRequest.newBuilder()
                .addHeader(RequestTracingFilter.TRACE_HEADER, traceId)
                .build()
        } ?: originalRequest
        return chain.proceed(newRequest)
    }
}
