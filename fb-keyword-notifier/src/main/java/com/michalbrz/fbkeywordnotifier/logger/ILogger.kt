package com.michalbrz.fbkeywordnotifier.logger

interface ILogger {
    fun debug(tag: String, message: String)

    fun info(tag: String, message: String)

    fun error(tag: String, message: String)

    fun json(tag: String, message: String)

    fun debug(message: String) = debug("Logger", message)

    fun info(message: String) = info("Logger", message)

    fun error(message: String) = error("Logger", message)

    fun json(message: String) = json("Logger", message)
}