package com.xhr.fzp.other

import android.os.Bundle
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityTestBinding
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Article
import com.xhr.fzp.logic.model.Video
import com.xhr.fzp.utils.LogUtil

class TestActivity : BaseActivity<ActivityTestBinding>() {
    private val articleList = ArrayList<Article>()
    private val videoList = ArrayList<Video>()

//    private val viewModel by lazy { ViewModelProvider(this)[TestViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(this, "onCreate")
        val bitmap = Repository.getUserAvatarFormLocal()
        binding.ivAvatar.setImageBitmap(bitmap)
    }

    override fun initView() {
//        val tagList = listOf<Tag>(Tag(0, "", "article", ""), Tag(17, "", "", ""))
//        val af = SourceFragment(tagList)
//        replaceFragment(R.id.fl_test, af)


//        binding.tvTest.text = viewModel.counter.toString()


//        initData()
//
//        val sfm = supportFragmentManager
//        val bt = sfm.beginTransaction()
//        val infoFragment = InfoFragment()
//        val articleTitle = "文章"
//        val videoTitle = "视频"
//        val articleFragment = InfoListFragment(ArticleAdapter(articleList))
//        val videoFragment = InfoListFragment(VideoAdapter(videoList))
//        infoFragment.addTitleFragment(articleTitle, articleFragment)
//        infoFragment.addTitleFragment(videoTitle, videoFragment)
//        bt.replace(R.id.fl_fragment, infoFragment)
//        bt.commit()
    }

    override fun initListener() {
//        binding.btnAdd.setOnClickListener {
//            viewModel.add()
//            refreshData()
//        }
//
//        binding.btnTest.setOnClickListener {
//            val intent = Intent(this, TestActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun refreshData(){
//        binding.tvTest.text = viewModel.counter.toString()
    }

    override fun initData() {
//        val article = Article(
//            "hhh",
//            "null",
//            123,
//            "test title",
//            Date(),
//            Date(),
//            Manager("admin", "官方"),
//            "normal",
//            Tag(123, "hh", "HH", "hh")
//        )
//        val video = Video(
//            "快来看",
//            123,
//            "视频",
//            Date(),
//            Date(),
//            Manager("admin", "官方"),
//            "normal",
//            Tag(123, "fdsf", "", "FDJFi")
//        )
//        videoList.add(video)
//        articleList.add(article)
    }


}