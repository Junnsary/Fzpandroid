package com.xhr.fzp.ui.knowledge.recommendation

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentRecommBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.ui.source.SourceAdapter

class RecommFragment : BaseFragment<FragmentRecommBinding>(), IRefresh {

    val viewModel by lazy { ViewModelProvider(this)[RecommViewModel::class.java]}

    private lateinit var adapter : SourceAdapter

    override fun initData() {
        viewModel.recommListLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null && data.isNotEmpty()) {
                viewModel.recommList.clear()
                viewModel.recommList.addAll(data)
                adapter.notifyDataSetChanged()
            }
            stopRefresh()
        }
    }

    override fun initView() {
        adapter = SourceAdapter(this, viewModel.recommList)
        binding.rvRecommendation.layoutManager = LinearLayoutManager(activity)
        binding.rvRecommendation.adapter = adapter
        refresh()
    }

    override fun initListener() {
        binding.srlRefreshData.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh() {
        binding.srlRefreshData.isRefreshing = true
        viewModel.getRecommList(10)
    }

    override fun stopRefresh() {
        binding.srlRefreshData.isRefreshing = false
    }

}