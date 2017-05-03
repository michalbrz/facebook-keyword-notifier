package com.michalbrz.fbnotifier.postslist

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.fanpage.FanpagesStorage
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage
import java.text.SimpleDateFormat
import java.util.*

class PostsActivityPresenter(postsListActivityView: PostsListActivityView,
                             facebookInfoRetrieverImpl: FacebookInfoRetriever,
                             fanpagesStorage: FanpagesStorage,
                             val keywordStorage: KeywordStorage) {

    val cutOffDate: Date by lazy { Date(Date().time - 1000 * 60 * 60 * 36) } // 36 hours ago

    init {
        val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()
        facebookInfoRetrieverImpl.getPostsForFanpages(favoriteFanpagesId) { fanpages ->
            if (fanpages.isEmpty()) {
                postsListActivityView.showToastWithMessage("Service temporarily unavailable")
            } else {
                postsListActivityView.displayPosts(flattenToViewModelAndFilter(fanpages))
            }
        }
    }

    private fun flattenToViewModelAndFilter(fanpages: List<Fanpage>) : List<PostViewModel> {
        return fanpages
                .flatMap { fanpage -> fanpage.posts.map { post -> toPostViewModelWithDate(fanpage, post) } }
                .filter { (post, date) -> date.after(cutOffDate) }
                .map { it.first }
                .sortedByDescending { it.dateAndTime }
                .toList()
    }

    private fun toPostViewModelWithDate(fanpage: Fanpage, post: Post): Pair<PostViewModel, Date> {
        val dateAndTime = SimpleDateFormat("MM.dd HH:mm").format(post.time)
        val hasKeywords = keywordStorage.getKeywords()
                .any { keyword -> post.text.toLowerCase().contains(keyword) }
        val postViewModel = PostViewModel(dateAndTime, post.text, post.postUrl, post.ImageUrl, fanpage.name, fanpage.pictureUrl, hasKeywords)
        return postViewModel to post.time
    }
}

