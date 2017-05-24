package com.michalbrz.fbnotifier.postslist.singlefanpage

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage
import com.michalbrz.fbnotifier.postslist.PostViewModel
import com.michalbrz.fbnotifier.postslist.PostsListView
import java.text.SimpleDateFormat

class SingleFanpagePostsPresenter(postsListView: PostsListView,
                                  fanpageId: String,
                                  facebookInfoRetrieverImpl: FacebookInfoRetriever,
                                  val keywordStorage: KeywordStorage) {

    init {
        facebookInfoRetrieverImpl.getPostsForFanpages(listOf(fanpageId)) { fanpages ->
            if (fanpages.isEmpty()) {
                postsListView.showToastWithMessage("Service temporarily unavailable")
            } else {
                postsListView.displayPosts(flattenToViewModelAndFilter(fanpages))
            }
        }
    }

    private fun flattenToViewModelAndFilter(fanpages: List<Fanpage>) : List<PostViewModel> {
        return fanpages
                .flatMap { fanpage -> fanpage.posts.map { post -> toPostViewModel(fanpage, post) } }
                .sortedByDescending { it.dateAndTime }
                .toList()
    }

    private fun toPostViewModel(fanpage: Fanpage, post: Post): PostViewModel {
        val dateAndTime = SimpleDateFormat("MM.dd HH:mm").format(post.time)
        val hasKeywords = keywordStorage.getKeywords().any(post::contains)
        return PostViewModel(dateAndTime, post.text, post.postUrl, post.ImageUrl, fanpage.name, fanpage.pictureUrl, hasKeywords)
    }

}