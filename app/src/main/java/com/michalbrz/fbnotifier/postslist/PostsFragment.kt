package com.michalbrz.fbnotifier.postslist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalbrz.fbkeywordnotifier.FacebookInfoRetrieverImpl
import com.michalbrz.fbkeywordnotifier.fanpage.DummyFanpagesStorage
import com.michalbrz.fbkeywordnotifier.storage.DummyKeywordStorage
import com.michalbrz.fbnotifier.FacebookApiAdapterImpl
import com.michalbrz.fbnotifier.R
import com.michalbrz.fbnotifier.toastWithMessage
import kotlinx.android.synthetic.main.activity_posts_list.*

class PostsFragment : Fragment(), PostsListView {

    private val postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.main_fragment)
        val view = inflater.inflate(R.layout.activity_posts_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        postsRecyclerView.layoutManager = LinearLayoutManager(context)
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