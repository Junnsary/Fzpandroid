package com.xhr.fzp.ui.source

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentSourceBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.logic.model.Tag

class SourceFragment(private val tags: List<Tag>) : BaseFragment<FragmentSourceBinding>(), IRefresh {

    private val viewModel by lazy { ViewModelProvider(this)[SourceViewModel::class.java] }
    private lateinit var adapter: SourceAdapter

    override fun initData() {
        adapter = SourceAdapter(this, viewModel.sourceList)

        viewModel.sourceListLD.observe(this) {
            val articles = it.getOrNull()
            if (articles != null && articles.isNotEmpty()) {
                viewModel.sourceList.clear()
                viewModel.sourceList.addAll(articles)
                adapter.notifyDataSetChanged()
            }
            stopRefresh()
        }
    }


    override fun initView() {
        refresh()
        binding.rvArticleList.adapter = adapter
        binding.rvArticleList.layoutManager = LinearLayoutManager(activity)
    }

    override fun initListener() {
        binding.srlRefreshData.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh() {
        viewModel.getSourceList(tags)
        binding.srlRefreshData.isRefreshing = true
    }

    override fun stopRefresh() {
        binding.srlRefreshData.isRefreshing = false
    }
}