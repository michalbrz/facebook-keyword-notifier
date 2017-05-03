package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.DataGeneration.fanpageWithPosts
import com.michalbrz.fbkeywordnotifier.fakes.InMemoryKeywordStorage
import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpages
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.mockito.Mockito

class BaseFacebookKeywordOccurrenceTest {

    val favoriteFanpages = mock<FavoriteFanpages>()
    val keywordOccurence = BaseFacebookKeywordOccurrence(
            favoriteFanpages,
            InMemoryKeywordStorage("keyword1", "keyword2"))
    val mockCallback = mock<(PostsWithFanpages) -> Unit>()

    @Test
    fun shouldNotCallCallbackWhenPostsDontContainKeyword() {
        callPostsWithKeywordsCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Some message",
                "Another message"
        )))

        keywordOccurence.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback, never()).invoke(any())
    }

    @Test
    fun shouldPassFormattedPostsWhenContainKeyword() {
        val fanpage = fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2"
        )
        callPostsWithKeywordsCallback(favoriteFanpages, listOf(fanpage))

        keywordOccurence.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke(check {
            it[0] shouldEqual (fanpage.posts[0] to fanpage)
            it[1] shouldEqual (fanpage.posts[1] to fanpage)
        })
    }

    @Test
    fun shouldReturnMessageForEveryPostContainingKeyword() {
        callPostsWithKeywordsCallback(favoriteFanpages, listOf(fanpageWithPosts(
                "Message with Keyword1",
                "Message with Keyword2",
                "Message with nothing"
        )))

        keywordOccurence.ifKeywordOccuredInPosts(mockCallback)

        verify(mockCallback).invoke( argThat { size == 2} )
    }

    private fun callPostsWithKeywordsCallback(favoriteFanpages: FavoriteFanpages, fanpages: List<Fanpage>) {
        Mockito.doAnswer { invocation ->
            val fanpagesProcessor: FanpagesProcessor = invocation.arguments[0] as FanpagesProcessor
            fanpagesProcessor(fanpages)
        }.whenever(favoriteFanpages).getAll(any())
    }
}