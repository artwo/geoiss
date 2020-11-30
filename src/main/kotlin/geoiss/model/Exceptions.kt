package geoiss.model

open class HttpErrorException(val code: Int, message: String, cause: Throwable? = null) :
    RuntimeException(message, cause)

class HttpClientErrorException(code: Int, message: String, cause: Throwable? = null) :
    HttpErrorException(code, message, cause)

class HttpServerErrorException(code: Int, message: String, cause: Throwable? = null) :
    HttpErrorException(code, message, cause)

class ObjectDeserializationException(message: String, cause: Throwable? = null) :
    IllegalArgumentException(message, cause)
