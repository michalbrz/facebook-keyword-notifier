package com.michalbrz.fbkeywordnotifier.keywordoccurrence

import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.storage.ShownNotificationsStorage

class AlreadyShownNotificationsFilter(val keywordsInPosts: IFacebookKeywordOccurrence,
                                      val shownNotificationsStorage: ShownNotificationsStorage) : IFacebookKeywordOccurrence {
    override fun ifKeywordOccuredInPosts(postsProcessor: (PostsWithFanpages) -> Unit) {
        val alreadyShownUrls = shownNotificationsStorage.getAlreadyShownUrls()
        keywordsInPosts.ifKeywordOccuredInPosts { postsWithFanpages ->
            val filteredPosts = postsWithFanpages
                    .filter { (post, _) -> !wasAlreadyNotified(post, alreadyShownUrls) }
                    .onEach { (post, _) -> saveAlreadyNotified(post) }
            postsProcessor(filteredPosts)
        }
    }

    private fun saveAlreadyNotified(post: Post) {
        shownNotificationsStorage.addAlreadyShownUrl(post.postUrl)
    }

    private fun wasAlreadyNotified(post: Post, alreadyShownUrls: Set<String>): Boolean {
        return alreadyShownUrls.contains(post.postUrl)
    }
}