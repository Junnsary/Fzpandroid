package com.xhr.fzp.ui.comment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentCommentBinding

class CommentFragment(private val sourceId: Int, private val tagId: Int): BaseFragment<FragmentCommentBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[CommentViewModel::class.java] }
    private lateinit var adapter: CommentAdapter

    override fun initData() {
        viewModel.commentListLd.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                viewModel.commentList.clear()
                viewModel.commentList.addAll(data)
                adapter.notifyDataSetChanged()
                //评论数量
                binding.tvCommentNum.text = data.size.toString()
            }
        }
    }

    override fun initView() {
        //获取评论列表
        viewModel.getCommentList(sourceId, tagId)
        adapter = CommentAdapter(this, viewModel.commentList)
        binding.rvComment.adapter = adapter
        binding.rvComment.layoutManager = LinearLayoutManager(activity)
    }
}