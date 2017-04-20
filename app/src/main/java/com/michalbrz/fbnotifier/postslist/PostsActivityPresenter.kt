package com.michalbrz.fbnotifier.postslist

import com.michalbrz.fbkeywordnotifier.FacebookInfoRetriever
import com.michalbrz.fbkeywordnotifier.model.Fanpage
import com.michalbrz.fbkeywordnotifier.model.Post
import com.michalbrz.fbnotifier.FanpagesStorage
import java.text.SimpleDateFormat

class PostsActivityPresenter(postsListActivityView: PostsListActivityView,
                             facebookInfoRetrieverImpl: FacebookInfoRetriever,
                             fanpagesStorage: FanpagesStorage) {

    init {
        val favoriteFanpagesId = fanpagesStorage.getFavoriteFanpagesId()
        facebookInfoRetrieverImpl.getPostsForFanpages(favoriteFanpagesId) { fanpages ->
            if (fanpages.isEmpty()) {
                postsListActivityView.showToastWithMessage("Service temporarily unavailable")
            } else {
                postsListActivityView.displayPosts(flattenToViewModel(fanpages))
            }
        }
    }

    private fun flattenToViewModel(fanpages: List<Fanpage>) : List<PostViewModel> {
        return fanpages.flatMap { fanpage ->
                    fanpage.posts.map { post -> toPostViewModel(fanpage, post) } }
                .sortedByDescending { it.dateAndTime }
                .toList()
    }

    private fun toPostViewModel(fanpage: Fanpage, post: Post): PostViewModel {
        val dateAndTime = SimpleDateFormat("MM.dd HH:mm").format(post.time)
        return PostViewModel(dateAndTime, post.text, post.postUrl, post.ImageUrl, fanpage.name, fanpage.pictureUrl)
    }
}

