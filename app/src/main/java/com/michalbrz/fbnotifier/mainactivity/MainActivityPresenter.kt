package com.michalbrz.fbnotifier.mainactivity

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesStorage

class MainActivityPresenter(val view: MainActivityView,
                            val facebookInfoRetriever: FacebookInfoRetriever,
                            val fanpagesStorage: FanpagesStorage) {

    val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()

    init {
        facebookInfoRetriever.getFanpagesInfo(favoriteFanpagesId) {
            fanpagesInfo -> view.displayFanpages(fanpagesInfo) }
    }
}

