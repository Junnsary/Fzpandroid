package com.xhr.fzp.ui.video

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentVideoBinding
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.LogUtil

class VideoFragment(private val sourceId: Int) : BaseFragment<FragmentVideoBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[VideoViewModel::class.java] }

    override fun initData() {
        LogUtil.d(this, sourceId.toString())
        viewModel.videoInfoLD.observe(this) { result ->
            val video = result.getOrNull()
            if (video != null) {
                binding.tvVideoDetailTitle.text = video.title
                binding.tvVideoManagerName.text = video.manager.name
                binding.tvVideoDetailDate.text = video.createdAt.toString()
                val url = FzpServiceCreator.getVideoFilePath(video.fileName)
                LogUtil.d(this, url)
                binding.sgpVideo.setUp(url, true, "")
            }
        }
    }
    override fun initView() {
        viewModel.getVideoInfo(sourceId)
    }

}