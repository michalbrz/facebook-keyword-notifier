package com.michalbrz.fbnotifier

interface ShownNotificationsStorage {
    fun getAlreadyShownUrls() : Set<String>

    fun addAlreadyShownUrl(url: String)
}