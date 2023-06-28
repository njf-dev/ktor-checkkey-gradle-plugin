package com.njf2016.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1

fun Application.configureRouting() {
    install(IgnoreTrailingSlash)
    routing {
        get("/") {
            call.respondHtml {
                body {
                    h1 { +"欢迎访问 ${call.request}${call.request.host()}:${call.request.port()}" }
                }
            }
        }
    }
}
