package com.xhr.fzp.ui.videodetail

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentVideoBinding
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.LogUtil

class VideoFragment(private val sourceId: Int) : BaseFragment<FragmentVideoBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[VideoViewModel::class.java] }

    override fun initData() {
//        LogUtil.d(this, sourceId.toString())
        viewModel.videoInfoLD.observe(this) { result ->
            val video = result.getOrNull()
            if (video != null) {
                binding.tvVideoDetailTitle.text = video.title
                binding.tvVideoManagerName.text = video.manager.name
                binding.tvVideoDetailDate.text = video.createdAt.toString()
                val url = FzpServiceCreator.getVideoFilePath(video.fileName)
                LogUtil.d(this, url)
                binding.sgpVideo.setUp(url, true, "")
                val coverImage = ImageView(activity)
                coverImage.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(FzpServiceCreator.getNetworkImageURL(video.cover)).into(coverImage)
                binding.sgpVideo.thumbImageView = coverImage
            }
        }
    }
    override fun initView() {
        viewModel.getVideoInfo(sourceId)
        binding.sgpVideo.backButton.visibility = View.VISIBLE
//        binding.sgpVideo.backButton.setOnClickListener {
//            activity?.finish()
//        }
        binding.sgpVideo.backButton.visibility = View.GONE
        binding.sgpVideo.titleTextView.visibility = View.GONE
        binding.sgpVideo.fullscreenButton.setOnClickListener {
            binding.sgpVideo.startWindowFullscreen(activity, true, true)
        }
        binding.sgpVideo.isShowFullAnimation = true
        binding.sgpVideo.isAutoFullWithSize = true
    }

    override fun onPause() {
        super.onPause()
        binding.sgpVideo.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        binding.sgpVideo.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        binding.sgpVideo.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos();
    }


}