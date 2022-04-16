package tk.xihantest


import io.ktor.network.tls.certificates.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import tk.xihantest.dao.UsersDatabaseFactory
import tk.xihantest.plugins.*

import java.io.File

fun main() {
    UsersDatabaseFactory.init()

    // 测试环境 127.0.0.1
    val hosts = "127.0.0.1"


    val environment = applicationEngineEnvironment {
        //log = LoggerFactory.getLogger("ktor.application")

        connector {
            host = hosts
            port = 8080
        }

        module {
            configureAuthentication()
            configureAdministration()
            configStartSyncAnimeData()
            configureRouting()
            configureSerialization()
            configureHTTP()
            configStatusPages()

        }
    }
    embeddedServer(Netty, environment).start(wait = true)

}
