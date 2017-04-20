package com.michalbrz.fbnotifier.postslist

interface PostsListActivityView {

    fun displayPosts(posts: List<PostViewModel>)

    fun showToastWithMessage(message: String)
}