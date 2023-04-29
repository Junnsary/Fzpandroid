package com.xhr.fzp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityDetailBinding
import com.xhr.fzp.utils.state.UserContext
import com.xhr.fzp.ui.articledetail.ArticleFragment
import com.xhr.fzp.ui.comment.CommentFragment
import com.xhr.fzp.ui.videodetail.VideoFragment
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.replaceFragment
import com.xhr.fzp.utils.showToast

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    /**
     * 评论用 sourceid 和 tagid 就可以
     * 文章用id就可以
     * 视频也用id
     */
    private var sourceId: Int = 0
    private var tagId: Int = 0
    private var type: Int = 0
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

        fun actionStart(context: Context, sourceId: Int, tagId: Int, type: Int) {
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
                binding.tvDetailTitile.text = "文章学习"
            }
            VIDEO -> {
                fragment = VideoFragment(sourceId)
//                binding.rlActionbar.visibility = View.GONE
                binding.tvDetailTitile.text = "视频学习"
            }
        }

        //判断是否收藏了
        viewModel.isUserFavouriteLD.observe(this) { result ->
            val data = result.getOrNull()
            data?.let {
                if (it) {
                    LogUtil.d(this, "已经收藏")
//                    binding.tvCollect.setTextColor(getColor(android.R.color.holo_red_dark))
                    gotFavourite()
                    isCollect = true
                }
            }
        }

        viewModel.getAddUserFavoritesLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                if (data.success) {
                    "添加收藏成功".showToast()
                    gotFavourite()
                    isCollect = true
                } else {
                    "添加收藏失败".showToast()
                }
                collectClick = true
            }
        }

        viewModel.getCancelUserFavoritesLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                if (data.success) {
                    "取消收藏成功".showToast()
                    noFavourite()
                    isCollect = false
                } else {
                    "取消收藏失败".showToast()
                }
                collectClick = true
            }
        }

        viewModel.getAddUserCommentLD.observe(this) { result ->
            val data = result.getOrNull()
            if (data != null) {
                if (data.success) {
                    getString(R.string.comment_send_success).showToast()
                    hideKeyboard(binding.etCommentContent.windowToken) // 隐藏输入法和et失去焦点
                    commentFragment.setSendComment(true)
                    commentFragment.viewModel.getCommentList(sourceId, tagId)
                    binding.etCommentContent.text.clear()
                    binding.nsCommentBottom.scrollTo(0, binding.flSourceComment.top)
                } else {
                    getString(R.string.comment_send_fail).showToast()
                }
            }
        }
    }

    override fun initView() {
        if (viewModel.isUserLogin()) {
//            LogUtil.d(this, "已登录")
            viewModel.isUserCollect(sourceId, tagId)
        }

        replaceFragment(R.id.fl_detail, fragment)

        //添加评论fragment
        commentFragment = CommentFragment(sourceId, tagId)
        replaceFragment(R.id.fl_source_comment, commentFragment)

        //设置 返回图标
        setSupportActionBar(binding.tlDetail)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }

    }

    override fun initListener() {

        binding.etCommentContent.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                LogUtil.d(this, "获得焦点！")
                UserContext.comment(this) {}
            } else {
                LogUtil.d(this, "失去焦点！")
            }
        }

        binding.etCommentContent.addTextChangedListener {
            it?.let {
                val content = it.toString().trim()
                if (content.isNotEmpty()) {
                    binding.btnCommentSend.visibility = View.VISIBLE
                } else {
                    binding.btnCommentSend.visibility = View.GONE
                }
            }
        }

        binding.btnCommentSend.setOnClickListener {
            val content = binding.etCommentContent.text.toString().trim()
            /**
             * 1. 评论，需要用户信息，源的id和tagid
             * 2. 在recycler中添加
             */
            viewModel.getAddUserComment(sourceId, tagId, content)
        }

        //设置 收藏 和 取消收藏
        binding.tlDetail.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_favorites -> {
                    UserContext.collect(this) {
                        if (collectClick) {
                            if (isCollect) {
                                viewModel.cancelUserFavorites(sourceId, tagId)
                            } else {
                                viewModel.addUserFavorites(sourceId, tagId)
                            }
                            collectClick = false
                        }
                    }
//                    it.setIcon(R.drawable.ic_favourite_red_38)
                }
            }
            true
        }

    }

    private fun gotFavourite() {
        binding.tlDetail.menu.getItem(0).setIcon(R.drawable.ic_favourite_red_38)
    }

    private fun noFavourite() {
        binding.tlDetail.menu.getItem(0).setIcon(R.drawable.ic_favourite_withe_38)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_detail, menu)
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (v != null) {
                    if (isShouldHideKeyboard(v, ev)) {
                        hideKeyboard(v.windowToken)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(token: IBinder) {
        binding.etCommentContent.clearFocus()
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null) {
            LogUtil.d(this, "当前的焦点： ${v::class.java.simpleName}")
            val l = intArrayOf(0, 0)
            binding.llFocus.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + binding.llFocus.height
            val right = left + binding.llFocus.width
            LogUtil.v(
                this,
                "ll-localtion: left:$left, top:${top}, bottom:${bottom}, right:${right}"
            )
            LogUtil.v(this, "event-x: ${event.x}, event-y: ${event.y}")
            return !(event.x <= right && event.x >= left && event.y >= top && event.y <= bottom)
//            val left = l[0]
//            val top = l[1]
//            val bottom: Int = top + v.height
//            val right: Int = left + binding.btnCommentSend.width
//            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserLogin()) {
//            LogUtil.d(this, "已登录")
            viewModel.isUserCollect(sourceId, tagId)
        } else {
            binding.etCommentContent.clearFocus()
        }
    }
}