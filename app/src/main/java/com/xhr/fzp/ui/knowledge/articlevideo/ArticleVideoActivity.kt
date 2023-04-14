package com.xhr.fzp.ui.knowledge.articlevideo

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityArticleVideoBinding
import com.xhr.fzp.ui.source.SourceFragment
import java.net.ConnectException

class ArticleVideoActivity : BaseActivity<ActivityArticleVideoBinding>() {
    companion object {
        const val TYPE = "type"
        const val ARTICLE = "article"
        const val VIDEO = "video"
        fun actionStart(context: Context, type: String) {
            val intent = Intent(context, ArticleVideoActivity::class.java).apply {
                putExtra(TYPE, type)
            }
            context.startActivity(intent)
        }
    }

    private val tags = arrayOf("文章学习", "视频学习")
    private val fragments = ArrayList<SourceFragment>()

    val viewModel by lazy { ViewModelProvider(this)[ArticleVideoViewModel::class.java] }
    private lateinit var type: String
    override fun initData() {
        type = intent.getStringExtra(TYPE)!!
        viewModel.getArticleVideoTagLD.observe(this) { result ->
            result.onSuccess { it ->
                binding.srlRefreshTag.isEnabled = false
                binding.lllTlVp.visibility = View.VISIBLE
                binding.tvFailure.visibility = View.GONE
                if (it.isNotEmpty()) {
                    it.forEach { tag ->
                        if (tag.type == ARTICLE) {
                            viewModel.articleTagList.add(tag)
                        } else {
                            viewModel.videoTagList.add(tag)
                        }
                    }
                    fragments.add(SourceFragment(viewModel.articleTagList))
                    fragments.add(SourceFragment(viewModel.videoTagList))
                    binding.vp2DataList.adapter = object : FragmentStateAdapter(this) {
                        override fun getItemCount() = fragments.size
                        override fun createFragment(position: Int) = fragments[position]
                    }
                    TabLayoutMediator(binding.tvTitle, binding.vp2DataList) { tab, position ->
                        tab.text = tags[position]
                    }.attach()
                    if (type == ARTICLE) {
                        binding.vp2DataList.setCurrentItem(0, false)
                    } else {
                        binding.vp2DataList.setCurrentItem(1, false)
                    }
                }
            }
            result.onFailure {
                if (it is ConnectException) {
                    binding.srlRefreshTag.isEnabled = true
                    binding.tvFailure.visibility = View.VISIBLE
                    binding.lllTlVp.visibility = View.GONE
                }
                binding.srlRefreshTag.isRefreshing = false
            }
        }
        viewModel.getArticleVideoTag()
    }

    override fun initView() {
        binding.srlRefreshTag.isEnabled = false
//        binding.tlArticleVideo.setNavigationIcon(R.id.)
        setSupportActionBar(binding.tlArticleVideo)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }
    }

    override fun initListener() {
        binding.srlRefreshTag.setOnRefreshListener {
            viewModel.getArticleVideoTag()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }
}