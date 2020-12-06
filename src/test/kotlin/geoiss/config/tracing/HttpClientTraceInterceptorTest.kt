package geoiss.config.tracing

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.verify.VerificationTimes

class HttpClientTraceInterceptorTest {

    private lateinit var mockServerClient: ClientAndServer

    @BeforeEach
    fun startMockServer() {
        mockServerClient = ClientAndServer.startClientAndServer(8080)
    }

    @AfterEach
    fun stopMockServer() {
        mockServerClient.stop()
    }

    @Test
    fun `Test http call with trace interceptor`() {
        val okHttpClient = okHttpClientWithInterceptor(RequestTrace("test-trace-id"))
        val testRequest = testRequest()

        okHttpClient.newCall(testRequest).execute()

        mockServerClient.verify(
            request()
                .withMethod("GET")
                .withPath("/anything")
                .withHeader("Authorization", "Bearer 1234")
                .withHeader("X-Correlation-ID", "test-trace-id"),
            VerificationTimes.once()
        )
    }

    @Test
    fun `Test http call with trace interceptor and no trace id`() {
        val okHttpClient = okHttpClientWithInterceptor(RequestTrace(null))
        val testRequest = testRequest()

        okHttpClient.newCall(testRequest).execute()

        mockServerClient.verify(
            request()
                .withMethod("GET")
                .withPath("/anything")
                .withHeader("Authorization", "Bearer 1234"),
            VerificationTimes.once()
        )
        mockServerClient.verify(
            request()
                .withHeader("X-Correlation-ID", "test-trace-id"),
            VerificationTimes.exactly(0)
        )
    }

    private fun okHttpClientWithInterceptor(requestTrace: RequestTrace): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpClientTraceInterceptor(requestTrace))
            .build()

    private fun testRequest(): Request {
        return Request.Builder()
            .url("http://localhost:8080/anything")
            .addHeader("Authorization", "Bearer 1234")
            .build()
    }
}
