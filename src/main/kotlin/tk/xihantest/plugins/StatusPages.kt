package tk.xihantest.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


import tk.xihantest.models.JsonResult


fun Application.configStatusPages(){
    install(StatusPages){

        exception<Throwable> { call, cause ->
            call.respond(JsonResult("","500",cause.message ?: "未知错误"))
        }
        exception<NotFoundException>{ call, cause ->
            call.respond(JsonResult("","404",cause.toString()))
        }
        exception<AuthenticationException> { call, cause ->
            call.respond(JsonResult("","401",cause.toString()))
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(JsonResult("","500",cause.toString()))
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(JsonResult("","404",status.toString()))
        }







    }


}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()