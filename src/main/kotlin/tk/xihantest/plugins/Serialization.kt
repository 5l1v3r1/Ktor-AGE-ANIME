package tk.xihantest.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import tk.xihantest.models.JsonResult
import tk.xihantest.models.User

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(json = json)
        register(ContentType.Application.Json,KotlinxSerializationConverter(json))
    }

    routing {
        get("/json/kotlinx-serialization") {
            call.respond(JsonResult("Hello, Kotlin/Ktor!"))
        }

    }
}

val json: Json = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true
    prettyPrint = false
    useArrayPolymorphism = false
    ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
    coerceInputValues = true // 如果JSON字段是Null则使用默认值
}

object AnySerializer : KSerializer<Any>{
    override val descriptor = PrimitiveSerialDescriptor("Any", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Any {
       return decoder.decodeString()
    }

    override fun serialize(encoder: Encoder, value: Any) {
        encoder.encodeString(value.toString())
    }


}