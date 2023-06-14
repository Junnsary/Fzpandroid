package com.xhr.fzp.ui.knowledge

import android.content.Intent
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentKnowledgeBinding
import com.xhr.fzp.ui.knowledge.articlevideo.ArticleVideoActivity
import com.xhr.fzp.ui.knowledge.recommendation.RecommendationFragment
import com.xhr.fzp.ui.login.LoginActivity
import com.xhr.fzp.utils.replaceFragment

class KnowledgeFragment : BaseFragment<FragmentKnowledgeBinding>() {

    override fun initView() {
        val recommendationFragment = RecommendationFragment()
        binding.tvTest.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
        replaceFragment(R.id.fl_recommendation, recommendationFragment)
    }

    override fun initListener() {
        binding.btnArticleList.setOnClickListener {
            activity?.let {
                ArticleVideoActivity.actionStart(it, ArticleVideoActivity.ARTICLE)
            }
        }
        binding.btnVideoList.setOnClickListener {
            activity?.let {
                ArticleVideoActivity.actionStart(it, ArticleVideoActivity.VIDEO)
            }
        }
    }
}