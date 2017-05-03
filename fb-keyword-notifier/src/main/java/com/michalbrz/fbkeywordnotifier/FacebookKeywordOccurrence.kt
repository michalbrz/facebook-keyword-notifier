package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpages
import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage
import com.michalbrz.fbkeywordnotifier.storage.ShownNotificationsStorage

typealias NotificationMessages = List<String>

class FacebookKeywordOccurrence(val favoriteFanpages: FavoriteFanpages,
                                val keywordStorage: KeywordStorage,
                                val shownNotificationsStorage: ShownNotificationsStorage) {
    fun ifKeywordOccuredInPosts(notificationDisplay: (NotificationMessages) -> Unit) {
        favoriteFanpages.getAll { fanpages ->
            val keywords = keywordStorage.getKeywords()
            val alreadyShownUrls = shownNotificationsStorage.getAlreadyShownUrls()
            val notificationsMessages = fanpages
                    .flatMap { fanpage -> fanpage.posts.map { it to fanpage } }
                    .filter { (post, _) -> postContainsKeyword(post, keywords) }
                    .filter { (post, _) -> !wasAlreadyNotified(post, alreadyShownUrls) }
                    .onEach { (post, _) -> setAsAlreadyNotified(post) }
                    .map { toNotificationMessage(it) }
            if (notificationsMessages.isNotEmpty()) {
                notificationDisplay(notificationsMessages)
            }
        }
    }

    private fun setAsAlreadyNotified(post: Post) {
        shownNotificationsStorage.addAlreadyShownUrl(post.postUrl)
    }

    private fun toNotificationMessage(postWithFanpage: Pair<Post, Fanpage>): String {
        val fanpageName = postWithFanpage.second.name.substringBefore(" ").removeSuffix(":")
        val postMessage = postWithFanpage.first.text
        return "$fanpageName: $postMessage"
    }

    private fun wasAlreadyNotified(post: Post, alreadyShownUrls: Set<String>): Boolean {
        return alreadyShownUrls.contains(post.postUrl)
    }

    private fun postContainsKeyword(post: Post, keywords: List<String>): Boolean {
        return keywords.any { post.text.toLowerCase().contains(it) }
    }

}