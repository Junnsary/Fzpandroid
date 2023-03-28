package com.xhr.fzp.ui.detail

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityDetailBinding
import com.xhr.fzp.ui.article.ArticleFragment
import com.xhr.fzp.ui.comment.CommentFragment
import com.xhr.fzp.ui.video.VideoFragment
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.replaceFragment

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    /**
     * 评论用 sourceid 和 tagid 就可以
     * 文章用id就可以
     * 视频也用id
     */
    private var sourceId : Int = 0
    private var tagId : Int = 0
    private var type : Int = 0
    private lateinit var commentFragment: CommentFragment
    private lateinit var fragment: Fragment

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
            ARTICLE -> fragment = ArticleFragment(sourceId)
            VIDEO -> fragment = VideoFragment(sourceId)
        }
    }

    override fun initView() {
        replaceFragment(R.id.fl_detail, fragment)

        //添加评论fragment
        commentFragment = CommentFragment(sourceId, tagId)
        replaceFragment(R.id.fl_article_comment, commentFragment)

        binding.tvReturn.setOnClickListener {
            finish()
        }
    }
}