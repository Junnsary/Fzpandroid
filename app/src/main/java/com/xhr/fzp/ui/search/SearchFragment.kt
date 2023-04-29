package com.xhr.fzp.ui.search

import android.view.View
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentSearchBinding
import com.xhr.fzp.ui.source.SourceAdapter
import javax.xml.transform.Source

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    private lateinit var adapter : SourceAdapter
    private val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }
    override fun initData() {
        viewModel.searchListLD.observe(this) { result ->
            result.onSuccess { data ->
                if (data.isNotEmpty()) {
                    viewModel.searchList.clear()
                    viewModel.searchList.addAll(data)
                    adapter.notifyDataSetChanged()
                    binding.tvNullResult.visibility = View.GONE
                    binding.rvSearchList.visibility = View.VISIBLE
                } else {
                    binding.tvNullResult.visibility = View.VISIBLE
                    binding.rvSearchList.visibility = View.GONE
                }
            }
        }
    }

    override fun initView() {
        adapter = SourceAdapter(this, viewModel.searchList)
        binding.rvSearchList.layoutManager = LinearLayoutManager(activity)
        binding.rvSearchList.adapter = adapter
    }

    override fun initListener() {
        binding.btnSearchSend.setOnClickListener {
            val keywords = binding.etSearchKeywords.text.toString().trim()
            if (keywords.isNotEmpty()) {
                viewModel.getSearchList(keywords)
            }
        }
    }
}