package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.facebookadapter.FacebookApiAdapter
import com.michalbrz.fbkeywordnotifier.fakes.InMemoryKeywordStorage
import com.michalbrz.fbkeywordnotifier.fakes.InMemoryShownNotificationsStorage
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.FacebookKeywordOccurrenceFactory
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.NotificationMessages
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.File



class FacebookKeywordOccurrenceFullTestWithJson {

    val facebookApiAdapter = mock<FacebookApiAdapter>()
    val facebookKeywordOccurrence = FacebookKeywordOccurrenceFactory.forSimpleStringPosts(
            facebookApiAdapter,
            DummyFanpagesStorage(),
            InMemoryKeywordStorage(listOf("singapore", "indonesia", "bali")),
            InMemoryShownNotificationsStorage()

    )
    val mockDisplayNotificationsCallback = mock<(NotificationMessages) -> Unit>()

    @Before
    fun before() {
        val fbApiResponse = readFile("api_response.json")
        callPostsWithKeywordCallback(facebookApiAdapter, fbApiResponse)
    }

    @Test
    fun shouldReturnMessageForEveryPostContainingKeyword() {
        facebookKeywordOccurrence.ifKeywordOccuredInPosts(mockDisplayNotificationsCallback)

        verify(mockDisplayNotificationsCallback).invoke( argThat { size == 3} )
    }

    @Test
    fun shouldReturnFormattedMessageForEveryPostContainingKeyword() {
        facebookKeywordOccurrence.ifKeywordOccuredInPosts(mockDisplayNotificationsCallback)

        verify(mockDisplayNotificationsCallback).invoke( check {
            it[0] shouldEqual "Fanpage1: Some flight to Singapore"
            it[1] shouldEqual "Fanpage2: Awesome flight to Indonesia"
        } )
    }

    @Test
    fun shouldNotReturnMessagesThatWereAlreadyShown() {
        facebookKeywordOccurrence.ifKeywordOccuredInPosts(mockDisplayNotificationsCallback)
        verify(mockDisplayNotificationsCallback).invoke( argThat { size == 3 })

        facebookKeywordOccurrence.ifKeywordOccuredInPosts { mockDisplayNotificationsCallback }
        verifyNoMoreInteractions(mockDisplayNotificationsCallback)
    }

    private fun callPostsWithKeywordCallback(facebookApiAdapter: FacebookApiAdapter, fbResponseJson: String) {
        Mockito.doAnswer { invocation ->
            val jsonProcessor: (String?) -> Unit = invocation.arguments[1] as (String?) -> Unit
            jsonProcessor(fbResponseJson)
        }.whenever(facebookApiAdapter).getJsonForPostsWithFanpagesId(any(), any())
    }

    fun readFile(filename: String) : String {
        val absolutePath = File("").absolutePath
        val moduleName = "/fb-keyword-notifier"
        val basePath = if (absolutePath.contains(moduleName)) absolutePath else absolutePath + moduleName

        val file = File(basePath + "/src/test/resources/" + filename)
        return file.readText()
    }
}