package com.xhr.fzp.ui.search

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentSearchBinding
import com.xhr.fzp.ui.source.SourceAdapter
import com.xhr.fzp.utils.showToast
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
            searchContent()
        }
        binding.etSearchKeywords.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchContent()
                true
            }
            false
        }
    }


    private fun searchContent() {
        val keywords = binding.etSearchKeywords.text.toString().trim()
        if (keywords.isNotEmpty()) {
            viewModel.getSearchList(keywords)
        } else {
            "请输入内容".showToast()
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        activity?.let {
            val systemService = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            systemService.hideSoftInputFromWindow(it.window.decorView.windowToken, 0)
        }
    }
}