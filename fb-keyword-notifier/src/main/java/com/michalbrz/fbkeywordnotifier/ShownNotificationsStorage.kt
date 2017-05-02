package com.michalbrz.fbkeywordnotifier

interface ShownNotificationsStorage {
    fun getAlreadyShownUrls() : Set<String>

    fun addAlreadyShownUrl(url: String)
}