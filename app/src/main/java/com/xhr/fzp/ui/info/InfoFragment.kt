package com.xhr.fzp.ui.info

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentInfoBinding
import com.xhr.fzp.logic.model.Tag
import com.xhr.fzp.ui.source.SourceFragment

/**
 * 创建info fragment类，可以选择创建
 * 1. 文章类 article + knowledge/case
 * 2. 视频类 video + knowledge/case
 * 3. 文章+视频类
 * 4. 文章混合视频类
 * 枚举是列举出所有的可能性
 *
 * 可以创建 一个 文章或者视频的，或者纯文章或视频，这三个需求
 *
 */
class InfoFragment : BaseFragment<FragmentInfoBinding> {

    private val type: String
    private val category: String
    private val sum: Boolean

    private constructor(type: String, category: String, sum: Boolean) : super() {
        this.type = type
        this.category = category
        this.sum = sum
    }

    companion object {
        const val ARTICLE = "article"
        const val VIDEO = "video"
        const val KNOWLEDGE = "knowledge"
        const val CASE = "case"
        const val ARTICLEVIDEO = "articlevideo"

        fun getVideoCase(sum: Boolean = false) = InfoFragment(ARTICLE, CASE, sum)
        fun getVideoArticleCase(sum: Boolean = false) = InfoFragment(ARTICLEVIDEO, CASE, sum)
    }

    private val viewModel by lazy { ViewModelProvider(this)[InfoViewModel::class.java] }

    override fun initData() {
        viewModel.TagListLD.observe(this) { result ->

            result.onSuccess {
                if (it.isNotEmpty()) {
                    binding.llTlVp.visibility = View.VISIBLE
                    binding.srlInfo.isEnabled = false
                    binding.tvFailure.visibility = View.GONE
                    viewModel.tagList.clear()
                    viewModel.tagList.addAll(it)
                    if (sum) {
                        viewModel.fragments.add(SourceFragment(it))
                        viewModel.tagList.add(0, Tag(0, "推荐", "article", "case"))
                    }
                    // 创建每个tag的fragment
                    it.forEach { tag ->
                        viewModel.fragments.add(SourceFragment(listOf(tag)))
                    }
                    //设置tablayout和viewpager
                    binding.vpInfoList.adapter = object : FragmentStateAdapter(this) {
                        override fun getItemCount() = viewModel.fragments.size
                        override fun createFragment(position: Int) = viewModel.fragments[position]
                    }
                    binding.vpInfoList.offscreenPageLimit = 1
                    TabLayoutMediator(binding.tlInfoTag, binding.vpInfoList) { tab, position ->
                        tab.text = viewModel.tagList[position].name
                    }.attach()
                }
            }

            result.onFailure {
                binding.tvFailure.visibility = View.VISIBLE
                binding.llTlVp.visibility = View.GONE
                binding.srlInfo.isEnabled = true
                binding.srlInfo.isRefreshing = false
            }
        }
        viewModel.getTagList(type, category)
    }

    override fun initView() {
        binding.srlInfo.isEnabled = false
    }

    override fun initListener() {
        binding.srlInfo.setOnRefreshListener {
            viewModel.getTagList(type, category)
        }
    }



}