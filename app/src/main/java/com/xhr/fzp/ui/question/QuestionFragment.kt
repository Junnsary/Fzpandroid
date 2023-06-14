package com.xhr.fzp.ui.question

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentQuestionBinding

class QuestionFragment(private val userId: String, private val review: Int): BaseFragment<FragmentQuestionBinding>() {
    private lateinit var adapter : QuestionAdapter

    private val viewModel by lazy { ViewModelProvider(this)[QuestionViewModel::class.java] }

    override fun initListener() {
        viewModel.questionListLD.observe(this) { it ->
            it.onSuccess {
                viewModel.questionList.clear()
                viewModel.questionList.addAll(it)
                adapter.notifyDataSetChanged()
            }
            binding.srlQuestion.isRefreshing = false
        }
    }

    override fun initView() {
        adapter = QuestionAdapter(viewModel.questionList, this)
        binding.rvQuestionList.layoutManager = LinearLayoutManager(activity)
        binding.rvQuestionList.adapter = adapter

        viewModel.getQuestionList(userId, review)
        binding.srlQuestion.isRefreshing = true

        binding.srlQuestion.setOnRefreshListener {
            viewModel.getQuestionList(userId, review)
        }
    }
}