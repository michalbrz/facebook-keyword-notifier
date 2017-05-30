package com.michalbrz.fbnotifier.notifications

import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage

class NotificationsPresenter(val notificationsView: NotificationsView, val keywordStorage: KeywordStorage) {

    fun init() {
        notificationsView.showKeywords(keywordStorage.getKeywords())
    }
}