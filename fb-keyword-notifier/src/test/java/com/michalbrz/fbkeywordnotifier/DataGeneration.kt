package com.michalbrz.fbkeywordnotifier

import com.michalbrz.fbkeywordnotifier.fanpage.Fanpage
import com.michalbrz.fbkeywordnotifier.fanpage.Post
import java.util.*

object DataGeneration {

    fun fanpageWithPosts(vararg posts: String) : Fanpage {
        return Fanpage("fanpageName", "pictureUrl", posts.toList().map { postWithMessage(it) })
    }

    fun fanpage(fanpageName: String) : Fanpage {
        return Fanpage(fanpageName, "pictureUrl", emptyList())
    }

    fun Fanpage.withPosts(vararg postsArg: Post) : Fanpage {
        return copy(posts = postsArg.toList())
    }

    fun postWithMessage(message: String) = Post(Date(), message, "imageUrl", "url://$message")

    fun newPost(message: String, url: String) = Post(Date(), message, "imageUrl", url)

}