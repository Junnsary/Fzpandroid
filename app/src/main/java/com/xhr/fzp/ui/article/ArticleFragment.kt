package com.xhr.fzp.ui.article

import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentArticleBinding
import com.xhr.fzp.logic.network.FzpServiceCreator

class ArticleFragment(private val sourceId: Int) : BaseFragment<FragmentArticleBinding>() {

    override fun initView() {
        //获取 网页
        binding.wvArticleContent.loadUrl(FzpServiceCreator.getArticleURL(sourceId))
    }
}