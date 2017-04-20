package com.michalbrz.fbkeywordnotifier.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.michalbrz.fbkeywordnotifier.PictureJson
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class FanpageJson (val posts: PostsDataJson,
                        val picture: PictureJson,
                        val name: String) {
    fun toFanpage() = Fanpage(name, picture.data.url, posts.data.map {  it.toPost()})
}

data class PostsDataJson(val data: List<PostJson>, val paging: PagingInfoJson)

data class PostJson (val message: String,
                     val picture: String,
                     val created_time: Date,
                     val id: String,
                     val permalink_url: String) {
    fun toPost() = Post(created_time, message, picture, permalink_url)
}

data class PagingInfoJson (val previous: String, val next: String)