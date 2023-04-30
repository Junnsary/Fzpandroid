package com.xhr.fzp.ui.question.myquestion

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityMyQuestionBinding
import com.xhr.fzp.ui.question.QuestionFragment
import com.xhr.fzp.utils.setToolbar

class MyQuestionActivity : BaseActivity<ActivityMyQuestionBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[MyQuestionViewModel::class.java] }

    override fun initView() {
        setToolbar(binding.tbMyQuestion, "我的提问")
        val userInfo = viewModel.getUserInfo()
        val title = arrayOf("审核通过", "正在审核", "审核未通过")
        val questions = arrayOf(QuestionFragment(userInfo.id, 1), QuestionFragment(userInfo.id, 0), QuestionFragment(userInfo.id, 2))
        binding.vpQuestionCategory.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount() = questions.size
            override fun createFragment(position: Int) = questions[position]
        }
        TabLayoutMediator(binding.tlQuestionCategory, binding.vpQuestionCategory){ tab, position ->
            tab.text = title[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}