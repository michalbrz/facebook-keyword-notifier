package com.michalbrz.fbnotifier.notifications

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalbrz.fbnotifier.R
import kotlinx.android.synthetic.main.keyword_item.view.*

class KeywordsAdapter : RecyclerView.Adapter<KeywordsAdapter.KeywordViewHolder>(){

    var keywords: MutableList<String> = mutableListOf()

    inner class KeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(keyword: String) {
            with(itemView) {
                keywordTextView.text = keyword
                removeKeywordButton.setOnClickListener {
                    val index = keywords.indexOf(keyword)
                    keywords.remove(keyword)
                    notifyItemRemoved(index)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.keyword_item, parent, false)
        return KeywordViewHolder(v)
    }

    override fun getItemCount() = keywords.size


    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

}