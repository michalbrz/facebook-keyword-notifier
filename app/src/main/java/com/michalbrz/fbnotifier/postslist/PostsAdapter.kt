package com.michalbrz.fbnotifier.postslist

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalbrz.fbnotifier.R
import com.michalbrz.fbnotifier.postslist.PostsAdapter.PostViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*


class PostsAdapter : RecyclerView.Adapter<PostViewHolder>() {

    var posts: List<PostViewModel> = emptyList()

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardWithKeywordColor = Color.parseColor("#CBE4CB")

        fun bind(post: PostViewModel) {
            with(itemView) {
                fanpageNameInPostTextView.text = post.fanpageName
                postContentTextView.text = post.text
                postDateTextView.text = post.dateAndTime
                Picasso.with(context).load(post.fanpagePictureUrl).into(fanpagePhotoInPostImageView)

                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.url))
                    startActivity(context, intent, null)}

                setCardBackgroundColor(post)
            }
        }

        private fun setCardBackgroundColor(post: PostViewModel) {
            with(itemView as CardView) {
                if (post.hasKeyword) {
                    setCardBackgroundColor(cardWithKeywordColor) // referencing xml color doesn't work
                } else {
                    setCardBackgroundColor(Color.WHITE)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(v)
    }

    override fun getItemCount(): Int = posts.size
}