package geoiss.service

import geoiss.config.CustomObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Rule
import org.junit.Test
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody.json
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class HttpClientTest {

    @get:Rule
    val mockServerRule = MockServerRule(this)
    private val mockServerClient = MockServerClient("localhost", mockServerRule.port)

    private val httpClient = HttpClient(OkHttpClient(), CustomObjectMapper, HttpClient.Properties())

    @Test
    fun `Test raw JSON response of http call`() {
        val expectedResponse = """{ "test": "response" } """
        mockServerClient.`when`(mockRequest()).respond(
            response()
                .withStatusCode(200)
                .withBody(json(expectedResponse))
        )

        val actualResponse = httpClient.executeRequestWithRawResponse(testRequest())
        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT)
    }

    private fun testRequest(): Request {
        return Request.Builder()
            .url("http://localhost:${mockServerRule.port}/anything")
            .addHeader("Content-type", "application/json")
            .addHeader("Authorization", "Bearer 1234")
            .method("POST", """{ "test": "request" }""".toRequestBody())
            .build()
    }

    private fun mockRequest() = request()
        .withMethod("POST")
        .withPath("/anything")
        .withHeader("Content-type", "application/json")
        .withHeader("Authorization", "Bearer 1234")
        .withBody(
            json("""{ "test": "request" }""")
        )
}
