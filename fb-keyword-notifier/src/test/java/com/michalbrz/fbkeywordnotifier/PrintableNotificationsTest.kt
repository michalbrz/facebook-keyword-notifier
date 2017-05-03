package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.DataGeneration.fanpage
import com.michalbrz.fbkeywordnotifier.DataGeneration.postWithMessage
import com.michalbrz.fbkeywordnotifier.DataGeneration.withPosts
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.IFacebookKeywordOccurrence
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.NotificationMessages
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.PostsWithFanpages
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.PrintableNotifications
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Test

class PrintableNotificationsTest {

    val keywordOccurence = mock<IFacebookKeywordOccurrence>()
    val printableNotifications = PrintableNotifications(keywordOccurence)

    @Test
    fun shouldTransformPostsToString() {
        val post1 = postWithMessage("post message")
        val post2 = postWithMessage("another post message")
        val fanpage = fanpage("fanpageName").withPosts(post1, post2)
        callKeywordOccurenceCallback(keywordOccurence, listOf((post1 to fanpage), (post2 to fanpage)))

        val callbackMock = mock<(NotificationMessages) -> Unit>()
        printableNotifications.ifKeywordOccuredInPosts(callbackMock)

        verify(callbackMock).invoke(check {
            it[0] shouldEqual "fanpageName: post message"
            it[1] shouldEqual "fanpageName: another post message"
        })
    }

    private fun callKeywordOccurenceCallback(keywordOccurence: IFacebookKeywordOccurrence, postsWithFanpages: PostsWithFanpages) {
        doAnswer { invocation ->
            val postWithFanpagesProcessor = invocation.arguments[0] as (PostsWithFanpages) -> Unit
            postWithFanpagesProcessor(postsWithFanpages)
        }.whenever(keywordOccurence).ifKeywordOccuredInPosts(any())
    }
}