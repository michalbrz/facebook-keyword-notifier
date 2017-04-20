package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever

class MainActivityPresenter(val view: MainActivityView,
                            val facebookInfoRetriever: FacebookInfoRetriever,
                            val fanpagesStorage: FanpagesStorage) {

    val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()


    init {
        facebookInfoRetriever.getFanpagesInfo(favoriteFanpagesId) { fanpagesInfo -> view.displayFanpages(fanpagesInfo) }
    }
}

