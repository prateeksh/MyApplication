package com.company.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.myapplication.R
import com.company.myapplication.model.News

class NewsAdapter (private val news: List<News>, private val context: Context): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    var onItemClick: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_view_items, parent, false)

        return NewsViewHolder(view)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

  /*      holder.matchId.text = matches.response!!.venues.get(position).id
        holder.matchName.text = matches.response!!.venues.get(position).name*/
        holder.tag.text = news[position].tag

        Glide
            .with(holder.itemView)
            .load(news[position].imageUri)
            .circleCrop()
            .into(holder.image)


        Glide
            .with(holder.itemView)
            .load(news[position].videoUri)
            .centerCrop()
            .fitCenter()
            .into(holder.video)
    }

    override fun getItemCount(): Int {

        return news.size
    }

    inner class NewsViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        val video : ImageView = itemView.findViewById(R.id.video_thumbnail_view)
        val image : ImageView = itemView.findViewById(R.id.user_image_view)
        val tag: TextView = itemView.findViewById(R.id.tag_view_saved)

        init {

            itemView.setOnClickListener {
                onItemClick?.invoke(news[adapterPosition])
            }
        }
    }
}