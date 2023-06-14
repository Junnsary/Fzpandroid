package com.xhr.fzp.ui.knowledge.recommendation

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentRecommBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.ui.source.SourceAdapter
import com.xhr.fzp.utils.LogUtil

class RecommendationFragment : BaseFragment<FragmentRecommBinding>(), IRefresh {

    val viewModel by lazy { ViewModelProvider(this)[RecommendationViewModel::class.java] }
    private lateinit var adapter: SourceAdapter
    override fun initData() {
        viewModel.recommListLD.observe(this) { result ->
            result.onSuccess {
                if (it.isNotEmpty()) {
                    updateSource(it)
                } else {
                    LogUtil.d(this, "获取不到数据")
                }
            }
            result.onFailure {
                showConnectError()
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

    private fun updateSource(data: List<Source>) {
        viewModel.recommList.clear()
        viewModel.recommList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun initListener() {
        binding.srlRefreshData.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh() {
        closeConnectError()
        binding.srlRefreshData.isRefreshing = true
        viewModel.getRecommList(10)
    }

    override fun stopRefresh() {
        binding.srlRefreshData.isRefreshing = false
    }

    private fun showConnectError() {
        binding.tvFailure.visibility = View.VISIBLE
    }

    private fun closeConnectError() {
        binding.tvFailure.visibility = View.GONE
    }
}