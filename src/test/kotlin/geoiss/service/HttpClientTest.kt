package geoiss.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.assertions.isSuccess
import geoiss.config.CustomObjectMapper
import geoiss.model.HttpClientErrorException
import geoiss.model.HttpServerErrorException
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

    @Test
    fun `Test http call with object deserialization`() {
        val expectedResponse = TestModel("response")
        mockServerClient.`when`(mockRequest()).respond(
            response()
                .withStatusCode(200)
                .withBody(json("""{ "test": "response" } """))
        )

        assertThat { httpClient.executeRequest<TestModel>(testRequest()) }
            .isSuccess()
            .isEqualTo(expectedResponse)
    }

    @Test
    fun `Test http call with 400`() {
        mockServerClient.`when`(mockRequest()).respond(
            response()
                .withStatusCode(400)
                .withBody(json("""{ "error": "response" } """))
        )

        assertThat { httpClient.executeRequest<TestModel>(testRequest()) }
            .isFailure()
            .isInstanceOf(HttpClientErrorException::class)
    }

    @Test
    fun `Test http call with 500`() {
        mockServerClient.`when`(mockRequest()).respond(
            response()
                .withStatusCode(500)
                .withBody(json("""{ "error": "response" } """))
        )

        assertThat { httpClient.executeRequest<TestModel>(testRequest()) }
            .isFailure()
            .isInstanceOf(HttpServerErrorException::class)
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

    private data class TestModel(val test: String)
}
