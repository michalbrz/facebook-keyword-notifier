package com.michalbrz.fbnotifier

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.FanpagesProcessor
import com.michalbrz.fbkeywordnotifier.model.Fanpage
import com.michalbrz.fbkeywordnotifier.model.Post
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class FacebookKeywordCheckerTest {

    val facebookInfoRetriever = mock<FacebookInfoRetriever>()
    val keywordStorage = mock<KeywordStorage>()
    val facebookKeywordChecker = FacebookKeywordChecker(facebookInfoRetriever, DummyFanpagesStorage(), keywordStorage)
    val mockCallback = mock<(NotificationMessages) -> Unit>()

    @Before
    fun before() {
        whenever(keywordStorage.getKeywords()).thenReturn(listOf("keyword1", "keyword2"))
    }

    @Test
    fun shouldNotCallCallbackWhenPostsDontContainKeyword() {
        callFanpagesReadyCallback(facebookInfoRetriever, listOf(fanpageWithPosts(
                "Some message",
                "Another message"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback, never()).invoke(any())
    }

    @Test
    fun shouldReturnMessageForEveryPostContainingKeyword() {
        callFanpagesReadyCallback(facebookInfoRetriever, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke( argThat { size == 2} )
    }

    @Test
    fun shouldReturnFormattedMessageForEveryPostContainingKeyword() {
        callFanpagesReadyCallback(facebookInfoRetriever, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke( check {
            it[0] shouldEqual "fanpageName: Message with Keyword1"
            it[1] shouldEqual "fanpageName: Message with Keyword2"
        } )
    }

    fun fanpageWithPosts(vararg posts: String) : Fanpage {
        return Fanpage("fanpageName", "pictureUrl", posts.toList().map { postWithMessage(it) })
    }

    fun postWithMessage(message: String) : Post {
        return Post(Date(), message, "imageUrl", "postUrl")
    }

    private fun callFanpagesReadyCallback(facebookInfoRetriever: FacebookInfoRetriever, fanpages: List<Fanpage>) {
        Mockito.doAnswer { invocation ->
            val fanpagesProcessor: FanpagesProcessor = invocation.arguments[1] as FanpagesProcessor
            fanpagesProcessor.invoke(fanpages)
            ""
        }.whenever(facebookInfoRetriever).getPostsForFanpages(any(), any())
    }
}