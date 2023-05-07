package com.xhr.fzp.ui.source

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.google.android.material.card.MaterialCardView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.ui.detail.DetailActivity
import com.xhr.fzp.utils.formatDate
import com.xhr.fzp.utils.setCoverImageOfSource
import com.xhr.fzp.utils.setMarginBottom

class SourceAdapter(private val fragment: Fragment, private val sourcesList: ArrayList<Source>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ARTICLE = 1
        private const val VIDEO = 2
    }
    inner class ArticleHolder(view: View) : RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.tv_article_title)
        val name : TextView = view.findViewById(R.id.tv_article_manager_name)
        val datetime : TextView = view.findViewById(R.id.tv_article_datetime)
        val cover : ImageView = view.findViewById(R.id.iv_article_cover)
    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.tv_video_title)
        val name : TextView = view.findViewById(R.id.tv_video_name)
        val datetime : TextView = view.findViewById(R.id.tv_video_datetime)
        val cover : ImageView = view.findViewById(R.id.iv_video_cover)
    }

    override fun getItemViewType(position: Int): Int {
        val type = sourcesList[position].tag.type
        return if (type == "article") {
            ARTICLE
        } else {
            VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == ARTICLE) {
        ArticleHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))
    } else {
        VideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
    }

    override fun getItemCount(): Int {
        return sourcesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setMarginBottom(position, sourcesList, holder, 10, itemCount)
        val source = sourcesList[position]
        when (holder) {
            is ArticleHolder -> {
                holder.title.text = source.title
                holder.name.text = source.manager.name
                holder.datetime.text = formatDate(source.createdAt)
                fragment.activity?.let { setCoverImageOfSource(source.cover, holder.cover, it) }
                holder.itemView.setOnClickListener {
                    fragment.activity?.let { it1 -> DetailActivity.actionStart(it1, source.id, source.tag.id, DetailActivity.ARTICLE) }
                }
            }
            is VideoHolder -> {
                holder.title.text = source.title
                holder.name.text = source.manager.name
                holder.datetime.text = formatDate(source.createdAt)
                fragment.activity?.let { setCoverImageOfSource(source.cover, holder.cover, it) }
                holder.itemView.setOnClickListener {
                    fragment.activity?.let { it1 -> DetailActivity.actionStart(it1, source.id, source.tag.id, DetailActivity.VIDEO) }
                }
            }
        }
    }
}