package com.michalbrz.fbnotifier.postslist

interface PostsListView {

    fun displayPosts(posts: List<PostViewModel>)

    fun showToastWithMessage(message: String)
}