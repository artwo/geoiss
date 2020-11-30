package geoiss.service

import geoiss.config.UrlProvider
import geoiss.model.IssResponseBody
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class IssService(
    private val urlProvider: UrlProvider,
    private val httpClient: HttpClient = HttpClient()
) {

    fun executeIssRequest(): IssResponseBody = httpClient.executeRequest(
        Request.Builder()
            .url("${urlProvider.issHost}/iss-now.json")
            .build(),
        "ISS API"
    )
}
