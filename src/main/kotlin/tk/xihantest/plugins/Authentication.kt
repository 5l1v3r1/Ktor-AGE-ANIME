package tk.xihantest.plugins

import com.sun.security.auth.UserPrincipal
import io.ktor.client.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureAuthentication(){
    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (credentials.password == "${credentials.name}123") UserIdPrincipal(credentials.name) else null
            }

        }

    }

    routing {
        authenticate("auth-basic") {
            get("/auth") {
                call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
            }
        }
    }


}

