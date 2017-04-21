package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever

class FacebookKeywordChecker(val facebookInfoRetriever: FacebookInfoRetriever,
                             val fanpagesStorage: FanpagesStorage,
                             val keywordStorage: KeywordStorage) {
    fun ifOccured() {
        val keywords = keywordStorage.getKeywords()
        facebookInfoRetriever.getPostsForFanpages(fanpagesStorage.getFavoriteFanpagesId()) { fanpages ->
            fanpages.flatMap { fanpage -> fanpage.posts.map { it to fanpage } }
                    .filter { (post, _) ->  keywords.any { post.text.contains(it) }}
//                    .
        }
    }

}