package com.michalbrz.fbnotifier.mainactivity

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.fanpage.FanpagesStorage

class MainFragmentPresenter(val view: MainFragmentView,
                            val facebookInfoRetriever: FacebookInfoRetriever,
                            val fanpagesStorage: FanpagesStorage) {

    val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()

    init {
        facebookInfoRetriever.getFanpagesInfo(favoriteFanpagesId) {
            fanpagesInfo -> view.displayFanpages(fanpagesInfo) }
    }
}

