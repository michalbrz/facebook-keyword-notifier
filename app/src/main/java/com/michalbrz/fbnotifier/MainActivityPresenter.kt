package com.michalbrz.fbnotifier

class MainActivityPresenter(val view: MainActivityView,
                            val facebookInfoRetriever: com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever,
                            val fanpagesStorage: FanpagesStorage) {

    val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()


    init {
        facebookInfoRetriever.getFanpagesInfo(favoriteFanpagesId) { fanpagesInfo -> view.displayFanpages(fanpagesInfo) }
    }
}

