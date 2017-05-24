package com.michalbrz.fbnotifier.postslist.singlefanpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbnotifier.FacebookApiAdapterImpl
import com.michalbrz.fbnotifier.R
import com.michalbrz.fbnotifier.postslist.PostViewModel
import com.michalbrz.fbnotifier.postslist.PostsAdapter
import com.michalbrz.fbnotifier.postslist.PostsListView
import com.michalbrz.fbnotifier.toastWithMessage
import kotlinx.android.synthetic.main.activity_posts_list.*

class SingleFanpagePostsListActivity : AppCompatActivity(), PostsListView {

    private val postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = postsAdapter
        SingleFanpagePostsPresenter(this,
                intent.getStringExtra(FANPAGE_ID_KEY),
                FacebookInfoRetrieverImpl(FacebookApiAdapterImpl()),
                DummyKeywordStorage())
    }

    override fun displayPosts(posts: List<PostViewModel>) {
        postsAdapter.posts = posts
        postsAdapter.notifyDataSetChanged()
    }

    override fun showToastWithMessage(message: String) {
        toastWithMessage(message)
    }

    companion object {

        const val FANPAGE_ID_KEY: String = "FANPAGE_ID_KEY"

        fun startActivityForFanpage(context: Context, fanpageId: String) {
            val intent = Intent(context, SingleFanpagePostsListActivity::class.java)
            intent.putExtra(FANPAGE_ID_KEY, fanpageId)
            context.startActivity(intent)
        }
    }
}
