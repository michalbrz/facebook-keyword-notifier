package com.michalbrz.fbkeywordnotifier.storage

interface ShownNotificationsStorage {
    fun getAlreadyShownUrls() : Set<String>

    fun addAlreadyShownUrl(url: String)
}