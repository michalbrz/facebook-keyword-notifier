package com.michalbrz.fbnotifier.postslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbnotifier.*
import kotlinx.android.synthetic.main.activity_posts_list.*

class PostsListActivity : AppCompatActivity(), PostsListView {

    private val postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = postsAdapter
        PostsPresenter(this,
                FacebookInfoRetrieverImpl(FacebookApiAdapterImpl()),
                DummyFanpagesStorage(),
                DummyKeywordStorage())
    }

    override fun displayPosts(posts: List<PostViewModel>) {
        postsAdapter.posts = posts
        postsAdapter.notifyDataSetChanged()
    }

    override fun showToastWithMessage(message: String) {
        toastWithMessage(message)
    }
}

