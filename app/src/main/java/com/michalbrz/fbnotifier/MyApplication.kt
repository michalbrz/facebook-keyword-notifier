package com.michalbrz.fbnotifier

import android.app.Application
import com.michalbrz.fbkeywordnotifier.Logger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.logger = AndroidLogger()
    }
}