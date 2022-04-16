package tk.xihantest.models


import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import tk.xihantest.plugins.AnySerializer


@Serializable
class HttpBinResponse(
    @Serializable(with = AnySerializer::class)
     var data: Any? = null,
     var code: String = "404",
     var msg: String = "error"
)

fun HttpBinResponse.clear() {
    data = null

}

/**
 * By default send what is expected for /get
 * Use a lambda to customize the response
 **/
suspend fun ApplicationCall.sendHttpBinResponse(configure: suspend HttpBinResponse.() -> Unit = {}) {
    val response = HttpBinResponse(
        code = "200",
        msg = "success"
    )
    response.configure()
    respond(response)
}