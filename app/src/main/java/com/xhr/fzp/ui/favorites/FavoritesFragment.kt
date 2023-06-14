package com.xhr.fzp.ui.favorites

import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentFavoritesBinding
import com.xhr.fzp.logic.model.Favorites

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val mFavoritesDataList = ArrayList<Favorites>()
    private val adapter =  FavoritesAdapter(mFavoritesDataList, this)

    override fun initView() {
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(activity)
    }

    fun updateAdapterData(newList: List<Favorites>) {
        mFavoritesDataList.clear()
        mFavoritesDataList.addAll(newList)
        adapter.notifyDataSetChanged()
    }

    override fun initListener() {
    }

}