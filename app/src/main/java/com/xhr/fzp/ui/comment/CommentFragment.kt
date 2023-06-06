package com.xhr.fzp.ui.comment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentCommentBinding

class CommentFragment(private val sourceId: Int, private val tagId: Int): BaseFragment<FragmentCommentBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[CommentViewModel::class.java] }
    private lateinit var adapter: CommentAdapter
    private var isSendComment = false

    override fun initData() {
        viewModel.commentListLd.observe(this) { result ->
            val data = result.getOrNull()
            result.onSuccess { data ->
                if (data.isNotEmpty()) {
                    binding.rvComment.visibility = View.VISIBLE
                    binding.tvNotComment.visibility = View.GONE
                    viewModel.commentList.clear()
                    if (isSendComment) {
                        val temp = data.toMutableList()
                        val last = temp.removeLastOrNull()
                        if (last != null) {
                            temp.add(0, last)
                        }
                        viewModel.commentList.addAll(temp)
                    } else {
                        viewModel.commentList.addAll(data)
                    }
                    adapter.notifyDataSetChanged()
//                binding.rvComment.scrollToPosition(adapter.itemCount - 1)
                    //评论数量
                    binding.tvCommentNum.text = data.size.toString()
                }
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

    fun setSendComment(flag: Boolean) {
        isSendComment = flag
    }

}