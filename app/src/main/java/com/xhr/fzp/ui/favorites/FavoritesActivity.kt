package com.xhr.fzp.ui.favorites

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityFavoritesBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.utils.LogUtil

class FavoritesActivity : BaseActivity<ActivityFavoritesBinding>(), IRefresh {
    private val title = arrayListOf("全部", "文章", "视频")
    private val num = mapOf(0 to title[0], 1 to title[1], 2 to title[2])
    private val fragments = HashMap<String, FavoritesFragment>()

    val viewModel by lazy { ViewModelProvider(this)[FavoritesViewModel::class.java] }

    override fun initData() {
        viewModel.favoritesListLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                LogUtil.d(this, data.all.size.toString())
                fragments["全部"]!!.updateAdapterData(data.all)
                fragments["文章"]!!.updateAdapterData(data.articles)
                fragments["视频"]!!.updateAdapterData(data.videos)
            }
            stopRefresh()
        }
    }

    override fun initView() {
        fragments[title[0]] = FavoritesFragment()
        fragments[title[1]] = FavoritesFragment()
        fragments[title[2]] = FavoritesFragment()

        binding.vpFavoritesList.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int): FavoritesFragment = fragments[num[position]]!!
        }
        TabLayoutMediator(binding.tlFavoritesTitle, binding.vpFavoritesList) { tab, position ->
            tab.text = title[position]
        }.attach()

        refresh()
    }

    override fun initListener() {
        binding.srlRefreshData.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh(){
        viewModel.getFavoritesList()
        binding.srlRefreshData.isRefreshing = true
    }

    override fun stopRefresh() {
        binding.srlRefreshData.isRefreshing = false
    }

}