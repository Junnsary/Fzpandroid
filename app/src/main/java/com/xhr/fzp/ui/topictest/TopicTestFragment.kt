package com.xhr.fzp.ui.topictest

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentTopicTestBinding
import com.xhr.fzp.logic.network.FzpServiceCreator

class TopicTestFragment : BaseFragment<FragmentTopicTestBinding>() {
    private val viewModel by lazy { ViewModelProvider(this)[TopicTestViewModel::class.java] }
    override fun initView() {
//        binding.wvTopicTest.webChromeClient = object : WebChromeClient() {
//            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
//
//            }
//        }
//        binding.wvTopicTest.settings.javaScriptEnabled = true
//        binding.wvTopicTest.loadUrl(FzpServiceCreator.getTopiTestUrl())
        binding.btnStartTest.setOnClickListener {
            activity?.let {
                val user = viewModel.getUserInfo()
                WebShowActivity.actionStart(it, FzpServiceCreator.getTopiTestUrl("topictest?userid=${user.id}"), "开始测试")
            }
        }
    }
}