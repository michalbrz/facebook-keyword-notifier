package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpages
import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class FacebookKeywordOccurrenceTest {

    val favoriteFanpages = mock<FavoriteFanpages>()
    val keywordStorage = mock<KeywordStorage>()
    val facebookKeywordChecker = FacebookKeywordOccurrence(favoriteFanpages, keywordStorage, InMemoryShownNotificationsStorage())
    val mockCallback = mock<(NotificationMessages) -> Unit>()

    @Before
    fun before() {
        whenever(keywordStorage.getKeywords()).thenReturn(listOf("keyword1", "keyword2"))
    }

    @Test
    fun shouldNotCallCallbackWhenPostsDontContainKeyword() {
        callFanpagesReadyCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Some message",
                "Another message"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback, never()).invoke(any())
    }

    @Test
    fun shouldReturnMessageForEveryPostContainingKeyword() {
        callFanpagesReadyCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke( argThat { size == 2} )
    }

    @Test
    fun shouldReturnFormattedMessageForEveryPostContainingKeyword() {
        callFanpagesReadyCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke( check {
            it[0] shouldEqual "fanpageName: Message with Keyword1"
            it[1] shouldEqual "fanpageName: Message with Keyword2"
        } )
    }

    @Test
    fun shouldNotReturnMessagesThatWereAlreadyShown() {
        callFanpagesReadyCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )))

        facebookKeywordChecker.ifKeywordOccuredInPosts(mockCallback)
        verify(mockCallback).invoke( argThat { size == 2 })

        facebookKeywordChecker.ifKeywordOccuredInPosts { mockCallback }
        verifyNoMoreInteractions(mockCallback)
    }

    fun fanpageWithPosts(vararg posts: String) : Fanpage {
        return Fanpage("fanpageName", "pictureUrl", posts.toList().map { postWithMessage(it) })
    }

    fun postWithMessage(message: String) : Post {
        return Post(Date(), message, "imageUrl", "url://$message")
    }

    private fun callFanpagesReadyCallback(favoriteFanpages: FavoriteFanpages, fanpages: List<Fanpage>) {
        Mockito.doAnswer { invocation ->
            val fanpagesProcessor: FanpagesProcessor = invocation.arguments[0] as FanpagesProcessor
            fanpagesProcessor.invoke(fanpages)
            ""
        }.whenever(favoriteFanpages).getAll(any())
    }
}