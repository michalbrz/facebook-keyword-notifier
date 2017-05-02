package com.michalbrz.fbnotifier

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.michalbrz.fbkeywordnotifier.ShownNotificationsStorage

class ShownNotificationsStorageImpl(context: Context) : ShownNotificationsStorage {

    companion object {
        val URLS_KEY = "URLS KEY"
    }

    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getAlreadyShownUrls(): Set<String> {
        return preferences.getStringSet(URLS_KEY, emptySet())
    }

    override fun addAlreadyShownUrl(url: String) {
        val editor = preferences.edit()
        val set: MutableSet<String> = getAlreadyShownUrls().toMutableSet()
        set.add(url)
        editor.putStringSet(URLS_KEY, set)
        editor.apply()

    }

}

