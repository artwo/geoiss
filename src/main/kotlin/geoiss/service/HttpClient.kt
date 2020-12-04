package geoiss.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import geoiss.model.HttpClientErrorException
import geoiss.model.HttpServerErrorException
import geoiss.model.ObjectDeserializationException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.io.IOException

class HttpClient(
    private val client: OkHttpClient = OkHttpClient(),
    val objectMapper: ObjectMapper = ObjectMapper(),
    val properties: Properties = Properties()
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    inline fun <reified T> executeRequest(request: Request, serverName: String? = null): T =
        executeRequestWithRawResponse(request, serverName)
            .deserializeResponse()

    fun executeRequestWithRawResponse(request: Request, serverName: String? = null): String? {
        if (properties.logBeforeRequest)
            log.info("Executing ${serverName.optionalText()} request [${request.method} ${request.url}${request.body?.let { " payload: ${request.body}" } ?: ""}]")

        request.execute(serverName).use {
            when {
                it.isSuccessful -> {
                    val responseBody = it.body?.string()
                    if (properties.logResponse)
                        log.info("${serverName.optionalText()} request responded with body $responseBody")
                    return responseBody
                }
                it.code in 400..499 -> throw HttpClientErrorException(
                    it.code,
                    "${serverName.optionalText()} request responded with a client error with code ${it.code}"
                )
                else -> throw HttpServerErrorException(
                    it.code,
                    "${serverName.optionalText()} request responded with a server error with code ${it.code}"
                )
            }
        }
    }

    private fun Request.execute(serverName: String?): Response = try {
        client.newCall(this).execute()
    } catch (e: IOException) {
        throw HttpServerErrorException(503, "Unable to reach the ${serverName.optionalText()} server", e)
    }

    inline fun <reified T> String?.deserializeResponse(): T = try {
        this?.let {
            objectMapper.readValue(it)
        } ?: throw ObjectDeserializationException("Cannot deserialize null to ${T::class.simpleName}")
    } catch (e: IOException) {
        throw ObjectDeserializationException("Unable to deserialize ${T::class.simpleName} object", e)
    }

    private fun String?.optionalText(): String = this?.let { this } ?: ""

    class Properties(
        var logBeforeRequest: Boolean = true,
        var logResponse: Boolean = false
    )
}
