package com.michalbrz.fbkeywordnotifier

object Logger : ILogger {
//kotlin delegation can't be used here, because deleagator can't be swaped at runtime,
// see: https://youtrack.jetbrains.com/issue/KT-5870

    var logger: ILogger = object : ILogger {
        override fun json(tag: String, message: String) = println("$tag: $message")

        override fun debug(tag: String, message: String) = println("$tag: $message")

        override fun info(tag: String, message: String) = println("$tag: $message")

        override fun error(tag: String, message: String) = println("$tag: $message")
    }

    override fun debug(tag: String, message: String) {
        logger.debug(tag, message)
    }

    override fun info(tag: String, message: String) {
        logger.info(tag, message)
    }

    override fun error(tag: String, message: String) {
        logger.error(tag, message)
    }

    override fun json(tag: String, message: String) {
        logger.json(tag, message)
    }
}

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