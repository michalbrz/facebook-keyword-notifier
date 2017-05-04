package com.michalbrz.fbkeywordnotifier.keywordoccurrence

import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post

class PrintableNotifications(val facebookKeywordOccurrence: IFacebookKeywordOccurrence) {
    fun ifKeywordOccurredInPosts(notificationDisplay: (NotificationMessages) -> Unit) {
        facebookKeywordOccurrence.ifKeywordOccuredInPosts { postsWithFanpages ->
            val notifications = postsWithFanpages.map { toNotificationMessage(it) }
            if (notifications.isNotEmpty()) notificationDisplay(notifications)
        }
    }

    private fun toNotificationMessage(postWithFanpage: Pair<Post, Fanpage>): String {
        val fanpageName = postWithFanpage.second.name.substringBefore(" ").removeSuffix(":")
        val postMessage = postWithFanpage.first.text
        return "$fanpageName: $postMessage"
    }
}