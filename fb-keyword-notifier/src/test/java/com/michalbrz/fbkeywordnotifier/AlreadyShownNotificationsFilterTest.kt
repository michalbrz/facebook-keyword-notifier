package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.DataGeneration.newPost
import com.michalbrz.fbkeywordnotifier.DataGeneration.withPosts
import com.michalbrz.fbkeywordnotifier.fakes.InMemoryShownNotificationsStorage
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.AlreadyShownNotificationsFilter
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.IFacebookKeywordOccurrence
import com.michalbrz.fbkeywordnotifier.keywordoccurrence.PostsWithFanpages
import com.michalbrz.fbkeywordnotifier.storage.ShownNotificationsStorage
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class AlreadyShownNotificationsFilterTest {

    val keywordOccurence = mock<IFacebookKeywordOccurrence>()
    val shownNotificationsStorage : ShownNotificationsStorage = InMemoryShownNotificationsStorage()
    val alreadyShownNotificationsFilter = AlreadyShownNotificationsFilter(keywordOccurence, shownNotificationsStorage)
    val callbackMock = mock<(PostsWithFanpages) -> Unit>()

    val url1 = "https://postUrl1"
    val url2 = "https://postUrl2"
    val post1 = newPost(message = "post message", url = url1)
    val post2 = newPost(message = "another post message", url = url2)
    val fanpage = DataGeneration.fanpage("fanpageName").withPosts(post1, post2)

    @Before
    fun before() {
        callKeywordOccurenceCallback(keywordOccurence, listOf((post1 to fanpage), (post2 to fanpage)))
    }

    @Test
    fun shouldNotShowNotificationIfItWasAlreadyShown() {
        shownNotificationsStorage.addAlreadyShownUrl(url1)

        alreadyShownNotificationsFilter.ifKeywordOccuredInPosts(callbackMock)

        verify(callbackMock).invoke( argThat { size == 1 })
    }

    @Test
    fun shouldShowNotificationsIfWasNotShownBefore() {
        alreadyShownNotificationsFilter.ifKeywordOccuredInPosts(callbackMock)

        verify(callbackMock).invoke(check {
            it[0] shouldEqual (post1 to fanpage)
            it[1] shouldEqual (post2 to fanpage)
        })
    }

    @Test
    fun shouldSaveNotificationsAsShownWhenTheyArePassedFurther() {
        alreadyShownNotificationsFilter.ifKeywordOccuredInPosts(callbackMock)

        shownNotificationsStorage.getAlreadyShownUrls() shouldContain url1
        shownNotificationsStorage.getAlreadyShownUrls() shouldContain url2
    }

    private fun callKeywordOccurenceCallback(keywordOccurence: IFacebookKeywordOccurrence, postsWithFanpages: PostsWithFanpages) {
        doAnswer { invocation ->
            val postWithFanpagesProcessor = invocation.arguments[0] as (PostsWithFanpages) -> Unit
            postWithFanpagesProcessor(postsWithFanpages)
        }.whenever(keywordOccurence).ifKeywordOccuredInPosts(any())
    }

}