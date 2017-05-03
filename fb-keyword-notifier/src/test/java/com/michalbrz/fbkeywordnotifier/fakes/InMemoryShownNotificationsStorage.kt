package com.michalbrz.fbkeywordnotifier.fakes

import com.michalbrz.fbkeywordnotifier.storage.ShownNotificationsStorage

class InMemoryShownNotificationsStorage : ShownNotificationsStorage {

    val urls = mutableSetOf<String>()

    override fun getAlreadyShownUrls() = urls

    override fun addAlreadyShownUrl(url: String) {
        urls.add(url)
    }
}