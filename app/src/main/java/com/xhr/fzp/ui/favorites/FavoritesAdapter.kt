package com.xhr.fzp.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xhr.fzp.R
import com.xhr.fzp.logic.model.Favorites
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.ui.detail.DetailActivity
import com.xhr.fzp.utils.formatDate
import com.xhr.fzp.utils.useGlideSetImage

class FavoritesAdapter(
    private val favoritesList: List<Favorites>,
    private val fragment: Fragment
    ) : RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder>() {

    inner class FavoritesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover : ImageView = view.findViewById(R.id.iv_favorites_cover)
        val title : TextView = view.findViewById(R.id.tv_favorites_title)
        val name : TextView = view.findViewById(R.id.tv_favorites_manager_name)
        val postDate : TextView = view.findViewById(R.id.tv_favorites_manager_datetime)
        val date : TextView = view.findViewById(R.id.tv_favorites_date)
        val play : RelativeLayout = view.findViewById(R.id.rl_play)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder {
        return FavoritesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false))
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        val favorites = favoritesList[position]
        if (favorites.tag.type == "article") {
            holder.itemView.setOnClickListener {
                fragment.activity?.let { it1 -> DetailActivity.actionStart(it1, favorites.source.id, favorites.source.tag.id, DetailActivity.ARTICLE) }
            }
            useGlideSetImage(fragment, FzpServiceCreator.getNetworkImageURL(favorites.source.cover), holder.cover)
        } else {
            holder.itemView.setOnClickListener {
                fragment.activity?.let { it1 -> DetailActivity.actionStart(it1, favorites.source.id, favorites.source.tag.id, DetailActivity.VIDEO) }
            }
            holder.play.visibility = View.VISIBLE
            useGlideSetImage(fragment, FzpServiceCreator.getNetworkImageURL(favorites.source.cover), holder.cover)
        }
        holder.date.text = formatDate(favorites.createdAt)
        holder.name.text = favorites.source.manager.name
        holder.title.text = favorites.source.title
        holder.postDate.text = formatDate((favorites.source.createdAt))
    }

}