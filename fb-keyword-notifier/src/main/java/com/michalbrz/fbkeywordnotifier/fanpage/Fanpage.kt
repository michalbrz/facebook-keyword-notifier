package com.michalbrz.fbkeywordnotifier.fanpage

import java.util.*

data class Fanpage(val name: String, val pictureUrl: String, val posts: List<Post>)

data class Post(val time: Date, val text: String, val ImageUrl: String, val postUrl: String) {
    fun contains(keyword: String): Boolean = text.toLowerCase().contains(keyword)
}

data class FanpageInfo(val name: String, val imageUrl: String, val likes: Int)
