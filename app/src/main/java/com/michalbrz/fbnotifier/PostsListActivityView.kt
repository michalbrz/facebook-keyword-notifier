package com.michalbrz.fbnotifier

interface PostsListActivityView {

    fun displayPosts(posts: List<PostViewModel>)

    fun showToastWithMessage(message: String)
}