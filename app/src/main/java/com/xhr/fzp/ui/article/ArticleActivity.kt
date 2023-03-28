package com.xhr.fzp.ui.article

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityArticleBinding
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.ui.comment.CommentFragment
import com.xhr.fzp.utils.replaceFragment

class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    private var sourceId : Int = 0
    private var tagId : Int = 0
    private lateinit var commentFragment: CommentFragment
    val viewModel by lazy { ViewModelProvider(this)[ArticleViewModel::class.java] }

    companion object {
        private const val SOURCE_ID = "SOURCE_ID"
        private const val TAG_ID = "TAG_ID"


        fun actionStart(context: Context, sourceId: Int, tagId: Int){
            val intent = Intent(context, ArticleViewModel::class.java).apply {
                putExtra(SOURCE_ID, sourceId)
                putExtra(TAG_ID, tagId)
            }
            context.startActivity(intent)
        }
    }

    override fun initData() {
        sourceId = intent.getIntExtra(SOURCE_ID, 4)
        tagId = intent.getIntExtra(TAG_ID, 11)
    }

    override fun initView() {
        super.initView()
        //获取 网页
        binding.wvContent.loadUrl(FzpServiceCreator.getArticleURL(sourceId))
        //添加评论fragment
        commentFragment = CommentFragment(sourceId, tagId)
        replaceFragment(R.id.fl_article_comment, commentFragment)
    }

}