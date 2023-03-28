package com.xhr.fzp.ui.info

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentInfoBinding
import com.xhr.fzp.logic.model.Tag
import com.xhr.fzp.ui.source.SourceFragment
import com.xhr.fzp.utils.LogUtil

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

        fun getVideoCase(sum: Boolean = false) = InfoFragment(ARTICLE, CASE, sum)
    }

    private val viewModel by lazy { ViewModelProvider(this)[InfoViewModel::class.java] }

    override fun initData() {
        viewModel.TagListLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                // 合并起来tag的一个fragment
                LogUtil.d(this, data.toString())
                viewModel.tagList.clear()
                viewModel.tagList.addAll(data)
                if (sum) {
                    viewModel.fragments.add(SourceFragment(data))
                    viewModel.tagList.add(0, Tag(0, "推荐", "article", ""))
                }
                // 创建每个tag的fragment
                data.forEach { tag ->
                    viewModel.fragments.add(SourceFragment(listOf(tag)))
                }
//                viewModel.fragments.add(SourceFragment(listOf(data[1])))
//                viewModel.fragments.add(SourceFragment(listOf(data[2])))
//                viewModel.fragments.add(SourceFragment(listOf(data[0])))
//                viewModel.fragments.add(SourceFragment(listOf(data[3])))

                //设置tablayout和viewpager
                binding.vpInfoList.adapter = object : FragmentStateAdapter(this) {
                    override fun getItemCount() = viewModel.fragments.size
                    override fun createFragment(position: Int) = viewModel.fragments[position]
                }
                binding.vpInfoList.offscreenPageLimit = 1
//                binding.vpInfoList.offscreenPageLimit = 4
                TabLayoutMediator(binding.tlInfoTag, binding.vpInfoList) { tab, position ->
                    tab.text = viewModel.tagList[position].name
                }.attach()
            }
        }
        viewModel.getTagList(type, category)
    }
}