package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.FavoriteFanpages
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import com.michalbrz.fbkeywordnotifier.storage.KeywordStorage

class BaseFacebookKeywordOccurrence(val favoriteFanpages: FavoriteFanpages,
                                    val keywordStorage: KeywordStorage) : IFacebookKeywordOccurrence {
    override fun ifKeywordOccuredInPosts(postsProcessor: (PostsWithFanpages) -> Unit) {
        favoriteFanpages.getAll { fanpages ->
            val keywords = keywordStorage.getKeywords()
            val notificationsMessages = fanpages
                    .flatMap { fanpage -> fanpage.posts.map { it to fanpage } }
                    .filter { (post, _) -> postContainsKeyword(post, keywords) }
            if (notificationsMessages.isNotEmpty()) {
                postsProcessor(notificationsMessages)
            }
        }
    }

    private fun postContainsKeyword(post: Post, keywords: List<String>): Boolean {
        return keywords.any { post.contains(it) }
    }
}