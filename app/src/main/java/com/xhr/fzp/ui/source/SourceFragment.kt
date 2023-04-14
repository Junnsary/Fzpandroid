package com.xhr.fzp.ui.source

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentSourceBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.logic.model.Tag

class SourceFragment(private val tags: List<Tag>) : BaseFragment<FragmentSourceBinding>(), IRefresh {

    val viewModel by lazy { ViewModelProvider(this)[SourceViewModel::class.java] }
    private lateinit var adapter: SourceAdapter

    override fun initData() {
        adapter = SourceAdapter(this, viewModel.mSourceList)

        viewModel.sourceListLD.observe(this) {
            val sources = it.getOrNull()
            if (sources != null && sources.isNotEmpty()) {
                updateAdapter(sources)
            }
            stopRefresh()
        }
    }

    private fun updateAdapter(data: List<Source>) {
        viewModel.mSourceList.clear()
        viewModel.mSourceList.addAll(data)
        adapter.notifyDataSetChanged()
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