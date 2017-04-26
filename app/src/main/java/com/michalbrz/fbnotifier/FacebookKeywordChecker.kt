package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.model.Fanpage
import com.michalbrz.fbkeywordnotifier.model.Post

typealias NotificationMessages = List<String>

class FacebookKeywordChecker(val facebookInfoRetriever: FacebookInfoRetriever,
                             val fanpagesStorage: FanpagesStorage,
                             val keywordStorage: KeywordStorage) {
    fun ifKeywordOccuredInPosts(notificationDisplay: (NotificationMessages) -> Unit) {
        facebookInfoRetriever.getPostsForFanpages(fanpagesStorage.getFavoriteFanpagesId()) { fanpages ->
            val keywords = keywordStorage.getKeywords()
            val notificationsMessages = fanpages
                    .flatMap { fanpage -> fanpage.posts.map { it to fanpage } }
                    .filter { (post, _) -> postContainsKeyword(post, keywords) }
                    .filter { (post, _) -> !wasAlreadyNotified(post) }
                    .map { toNotificationMessage(it) }
            if (notificationsMessages.isNotEmpty()) {
                notificationDisplay.invoke(notificationsMessages)
            }
        }
    }

    private fun toNotificationMessage(postWithFanpage: Pair<Post, Fanpage>): String {
        val fanpageName = postWithFanpage.second.name.substringBefore(" ").removeSuffix(":")
        val postMessage = postWithFanpage.first.text
        return "$fanpageName: $postMessage"
    }

    private fun wasAlreadyNotified(post: Post): Boolean {
        return false;
    }

    private fun postContainsKeyword(post: Post, keywords: List<String>): Boolean {
        return keywords.any { post.text.toLowerCase().contains(it) }
    }

}