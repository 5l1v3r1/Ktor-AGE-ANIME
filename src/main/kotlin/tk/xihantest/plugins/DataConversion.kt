package tk.xihantest.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.dataconversion.*
import tk.xihantest.models.User

fun Application.configDataConversion(){
    install(DataConversion){

    }
}