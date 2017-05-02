package com.michalbrz.fbnotifier.mainactivity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalbrz.fbkeywordnotifier.model.FanpageInfo
import com.michalbrz.fbnotifier.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fanpage_item.view.*

class FanpagesAdapter : RecyclerView.Adapter<FanpagesAdapter.FanpageViewHolder>() {

    var fanpages : List<FanpageInfo> = emptyList()

    class FanpageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fanpageInfo: FanpageInfo) {
            with(itemView) {
                fanpageNameTextView.text = fanpageInfo.name
                fanpageLikesTextView.text = "${fanpageInfo.likes} likes"
                Picasso.with(context).load(fanpageInfo.imageUrl).into(fanpagePhotoImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FanpageViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fanpage_item, parent, false)
        return FanpageViewHolder(v)
    }

    override fun onBindViewHolder(holder: FanpageViewHolder, position: Int) {
        holder.bind(fanpages[position])
    }

    override fun getItemCount(): Int = fanpages.size

}
