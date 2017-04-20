package com.michalbrz.fbkeywordnotifier.model

import java.util.*

data class Fanpage(val name: String, val pictureUrl: String, val posts: List<Post>)

data class Post(val time: Date, val text: String, val ImageUrl: String, val postUrl: String)

data class FanpageInfo(val name: String, val imageUrl: String, val likes: Int)
