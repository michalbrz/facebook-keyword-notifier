package com.michalbrz.fbkeywordnotifier.keywordoccurrence

import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post

interface IFacebookKeywordOccurrence {
    fun ifKeywordOccuredInPosts(postsProcessor: (PostsWithFanpages) -> Unit)
}

typealias NotificationMessages = List<String>
typealias PostsWithFanpages = List<Pair<Post, Fanpage>>