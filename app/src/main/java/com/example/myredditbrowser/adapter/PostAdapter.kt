package com.example.myredditbrowser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myredditbrowser.R
import com.example.myredditbrowser.model.Child
import com.example.myredditbrowser.model.Data_
import kotlinx.android.synthetic.main.post_view.view.*

class PostAdapter(var results: MutableList<Child>, val delegate: RedditAdapterDelegate) :
    RecyclerView.Adapter<PostAdapter.RedditCardHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditCardHolder {
        context = parent.context.applicationContext
        return RedditCardHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false))
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: RedditCardHolder, position: Int) {
        holder.username.text = results[position].data.author
        holder.textview.text = results[position].data.selftext
        holder.comment.text = context.getString(R.string.post_comments, results[position].data.numComments)
        holder.ups.text = context.getString(R.string.post_ups, results[position].data.ups)
        holder.downs.text = context.getString(R.string.post_downs, results[position].data.downs)

        if(results[position].data.thumbnail.isNotBlank()) {
            Glide.with(holder.itemView).load(results[position]).circleCrop().into(holder.avatar)
        }

        holder.itemView.setOnLongClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, results[position].data.selftext)

            Intent.createChooser(sendIntent, "Send Post")

            true
        }
    }

    interface RedditAdapterDelegate {
        fun redditCallback(data_: Data_)
    }

    class RedditCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.post_avatarView
        val username = itemView.post_nameView
        val textview = itemView.post_contentView
        val comment = itemView.post_commentView
        val ups = itemView.post_upsView
        val downs = itemView.post_downsView
    }

}