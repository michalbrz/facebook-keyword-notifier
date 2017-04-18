package com.michalbrz.fbkeywordnotifier.model

data class Fanpage(val fanpageInfo: FanpageInfo, val posts: List<Post>)

data class Post(val time: Int, val text: String, val ImageUrl: String, val postUrl: String)

data class FanpageInfo(val name: String, val imageUrl: String, val likes: Int)
