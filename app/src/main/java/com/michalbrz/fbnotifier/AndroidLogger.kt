package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.ILogger
import com.orhanobut.logger.Logger

class AndroidLogger : ILogger {
    override fun debug(tag: String, message: String) {
        Logger.d(message)
    }

    override fun info(tag: String, message: String) {
        Logger.i(message)
    }

    override fun error(tag: String, message: String) {
        Logger.e(message)
    }

    override fun json(tag: String, message: String) {
        Logger.json(message)
    }
}