package com.xhr.fzp.ui.knowledge.recommendation

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentRecommBinding
import com.xhr.fzp.ui.source.SourceAdapter

class RecommFragment : BaseFragment<FragmentRecommBinding>() {

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
        }

    }

    override fun initView() {
        adapter = SourceAdapter(this, viewModel.recommList)
        binding.rvRecommendation.layoutManager = LinearLayoutManager(activity)
        binding.rvRecommendation.adapter = adapter
        viewModel.getRecommList(10)
    }

}