package com.xhr.fzp.ui.articledetail

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentArticleBinding
import com.xhr.fzp.logic.network.FzpServiceCreator

class ArticleFragment(private val sourceId: Int) : BaseFragment<FragmentArticleBinding>() {

    override fun initView() {
        //获取 网页
        binding.wvArticleContent.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.wvArticleContent.visibility = View.VISIBLE
                binding.pbLoadingPage.visibility = View.GONE
            }
        }
        binding.wvArticleContent.loadUrl(FzpServiceCreator.getArticleURL(sourceId))
    }
}