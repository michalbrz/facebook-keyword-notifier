package com.michalbrz.fbkeywordnotifier.keywordoccurrence

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.facebookadapter.FacebookApiAdapter
import com.michalbrz.fbkeywordnotifier.fanpage.FanpagesStorage
import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpages
import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpagesImpl
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage
import com.michalbrz.fbkeywordnotifier.storage.ShownNotificationsStorage

class FacebookKeywordOccurrenceFactory {

    companion object {
        fun forSimpleStringPosts(facebookApiAdapter: FacebookApiAdapter,
                                 fanpagesStorage: FanpagesStorage,
                                 keywordStorage: KeywordStorage,
                                 shownNotificationsStorage: ShownNotificationsStorage): PrintableNotifications {
            val favoriteFanpages = FavoriteFanpagesImpl(
                    FacebookInfoRetrieverImpl(facebookApiAdapter),
                    fanpagesStorage)
            return FacebookKeywordOccurrenceFactory.forSimpleStringPosts(favoriteFanpages, keywordStorage, shownNotificationsStorage)
        }

        fun forSimpleStringPosts(favoriteFanpages: FavoriteFanpages,
                                 keywordStorage: KeywordStorage,
                                 shownNotificationsStorage: ShownNotificationsStorage): PrintableNotifications {
            return PrintableNotifications(
                    AlreadyShownNotificationsFilter(
                            BaseFacebookKeywordOccurrence(
                                    favoriteFanpages,
                                    keywordStorage),
                            shownNotificationsStorage))
        }
    }
}