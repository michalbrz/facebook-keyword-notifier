package com.michalbrz.fbkeywordnotifier.logger

import com.michalbrz.fbkeywordnotifier.logger.ILogger

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

