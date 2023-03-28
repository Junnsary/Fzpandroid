package com.xhr.fzp.ui.knowledge.articlevideo

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityArticleVideoBinding
import com.xhr.fzp.ui.source.SourceFragment
import com.xhr.fzp.utils.LogUtil

class ArticleVideoActivity : BaseActivity<ActivityArticleVideoBinding>() {
    companion object{
        const val TYPE = "type"
        const val ARTICLE = "article"
        const val VIDEO = "video"
        fun actionStart(context: Context, type: String){
            val intent = Intent(context, ArticleVideoActivity::class.java).apply {
                putExtra(TYPE, type)
            }
            context.startActivity(intent)
        }
    }
    val viewModel by lazy { ViewModelProvider(this)[ArticleVideoViewModel::class.java]}
    private var type : String? = null
    override fun initData() {
        type = intent.getStringExtra(TYPE)
        viewModel.getArticleVideoTagLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null && data.isNotEmpty()) {
                data.forEach { tag ->
                    LogUtil.d(this, tag.toString())
                    if (tag.type == ARTICLE) {
                        viewModel.articleTagList.add(tag)
                    } else {
                        viewModel.videoTagList.add(tag)
                    }
                }
                viewModel.fragments.add(SourceFragment(viewModel.articleTagList))
                viewModel.fragments.add(SourceFragment(viewModel.videoTagList))
                binding.vp2DataList.adapter = object : FragmentStateAdapter(this){
                    override fun getItemCount(): Int {
                        return viewModel.fragments.size
                    }
                    override fun createFragment(position: Int): Fragment {
                        return viewModel.fragments[position]
                    }
                }
                TabLayoutMediator(binding.tvTitle, binding.vp2DataList) { tab, position ->
//                    com.xhr.fzp.utils.LogUtil(this, viewModel.tags[position])
                    tab.text = viewModel.tags[position]
                }.attach()

                if (type != null) {
                    if (type == ARTICLE) {
                        binding.vp2DataList.setCurrentItem(0, false)
                    } else {
                        binding.vp2DataList.setCurrentItem(1, false)
                    }
                }
            }
        }
        viewModel.getArticleVideoTag()
    }
    override fun initView() {

    }

}