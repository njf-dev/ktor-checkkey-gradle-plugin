package com.njf2016

import io.ktor.server.application.*
import com.njf2016.plugins.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureHTTP()
    configureRouting()
}
