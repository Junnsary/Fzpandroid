package com.xhr.fzp.ui.detail

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityDetailBinding
import com.xhr.fzp.mode.state.UserContext
import com.xhr.fzp.ui.article.ArticleFragment
import com.xhr.fzp.ui.comment.CommentFragment
import com.xhr.fzp.ui.video.VideoFragment
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.replaceFragment
import com.xhr.fzp.utils.showToast

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    /**
     * 评论用 sourceid 和 tagid 就可以
     * 文章用id就可以
     * 视频也用id
     */
    private var sourceId : Int = 0
    private var tagId : Int = 0
    private var type : Int = 0
    private var isCollect = false
    private var collectClick = true
    private lateinit var commentFragment: CommentFragment
    private lateinit var fragment: Fragment
    val viewModel by lazy { ViewModelProvider(this)[DetailViewModel::class.java] }

    companion object {
        private const val SOURCE_ID = "SOURCE_ID"
        private const val TAG_ID = "TAG_ID"
        private const val TYPE = "TYPE"
        const val ARTICLE = 1
        const val VIDEO = 2

        fun actionStart(context: Context, sourceId: Int, tagId: Int, type: Int){
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(SOURCE_ID, sourceId)
                putExtra(TAG_ID, tagId)
                putExtra(TYPE, type)
            }
            context.startActivity(intent)
        }
    }

    override fun initData() {
        sourceId = intent.getIntExtra(SOURCE_ID, 4)
        tagId = intent.getIntExtra(TAG_ID, 11)
        type = intent.getIntExtra(TYPE, 0)
        LogUtil.d(this, "${sourceId} , ${tagId} ")
        when (type) {
            ARTICLE -> {
                fragment = ArticleFragment(sourceId)
//                binding.tvTypeTitle.text = "文章学习"
            }
            VIDEO ->{
                fragment = VideoFragment(sourceId)
//                binding.rlActionbar.visibility = View.GONE
//                binding.tvTypeTitle.text = "视频学习"
            }
        }

        viewModel.isUserCollectLD.observe(this) { result ->
            val data = result.getOrNull()
            data?.let {
                if (it) {
                    LogUtil.d(this, "已经收藏")
                    binding.tvCollect.setTextColor(getColor(android.R.color.holo_red_dark))
                    isCollect = true
                }
            }
        }

        viewModel.getAddUserCollectionLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                if (data.success) {
                    "添加收藏成功".showToast()
                    binding.tvCollect.setTextColor(getColor(android.R.color.holo_red_dark))
                    isCollect = true
                } else {
                    "添加收藏失败".showToast()
                }
                collectClick = true
            }
        }

        viewModel.getCancelUserCollectionLD.observe(this) {result ->
            val data = result.getOrNull()
            if (data != null) {
                if (data.success) {
                    "取消收藏成功".showToast()
                    binding.tvCollect.setTextColor(getColor(R.color.black))
                    isCollect = false
                } else {
                    "取消收藏失败".showToast()
                }
                collectClick = true
            }
        }
    }

    override fun initView() {
        if (viewModel.isUserLogin()) {
//            LogUtil.d(this, "已登录")
            viewModel.isUserCollect(sourceId, tagId)
        }

        binding.tvCollect.setOnClickListener {
            UserContext.collect(this) {
                if (collectClick) {
                    if (isCollect) {
                        viewModel.cancelUserCollection(sourceId, tagId)
                    } else {
                        viewModel.addUserCollection(sourceId, tagId)
                    }
                    collectClick = false
                }
            }
        }

        replaceFragment(R.id.fl_detail, fragment)

        //添加评论fragment
        commentFragment = CommentFragment(sourceId, tagId)
        replaceFragment(R.id.fl_article_comment, commentFragment)

        binding.tvReturn.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserLogin()) {
//            LogUtil.d(this, "已登录")
            viewModel.isUserCollect(sourceId, tagId)
        }
    }
}