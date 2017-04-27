package com.michalbrz.fbnotifier

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ShownNotificationsStorageImpl(context: Context) : ShownNotificationsStorage {

    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val URLS_KEY = "URLS KEY"

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

