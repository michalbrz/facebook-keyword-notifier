package com.michalbrz.fbkeywordnotifier.fanpage

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesInfoProcessor
import com.michalbrz.fbkeywordnotifier.FanpagesProcessor

class FavoriteFanpagesImpl(val facebookInfoRetriever: FacebookInfoRetriever,
                           val fanpagesStorage: FanpagesStorage) : FavoriteFanpages {

    override fun getAll(fanpagesProcessor: FanpagesProcessor) {
        facebookInfoRetriever.getPostsForFanpages(fanpagesStorage.getFavoriteFanpagesId(), fanpagesProcessor)
    }

    override fun getFanpagesInfo(fanpagesInfoProcessor: FanpagesInfoProcessor) {
        facebookInfoRetriever.getFanpagesInfo(fanpagesStorage.getFavoriteFanpagesId(), fanpagesInfoProcessor)
    }
}