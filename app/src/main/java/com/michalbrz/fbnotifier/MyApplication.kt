package com.michalbrz.fbnotifier

import android.app.Application
import com.michalbrz.fbkeywordnotifier.logger.Logger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.logger = AndroidLogger()
    }
}