package com.xhr.fzp.ui.question.questiondetail

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityQuestionDetailBinding
import com.xhr.fzp.logic.model.Answer
import com.xhr.fzp.logic.model.Question
import com.xhr.fzp.logic.network.FzpServiceCreator
import com.xhr.fzp.utils.*
import com.xhr.fzp.utils.state.UserContext
import java.util.*

class QuestionDetailActivity : BaseActivity<ActivityQuestionDetailBinding>() {

    companion object {
        fun actionStart(context: Context, question: Question) {
            val intent = Intent(context, QuestionDetailActivity::class.java).apply {
                putExtra("question", question)
            }
            context.startActivity(intent)
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[QuestionDetailViewModel::class.java] }
    private lateinit var adapter: AnswerAdapter
    private var question: Question? = null
    private lateinit var answer: Answer
    private lateinit var loadingDialog : LoadingDialog

    override fun initData() {
        viewModel.answerListLD.observe(this) { result ->
            result.onSuccess { data ->
                if (data.isNotEmpty()) {
                    binding.rvAnswer.visibility = View.VISIBLE
                    binding.tvNotAnswer.visibility = View.GONE
                    viewModel.answerList.clear()
                    viewModel.answerList.addAll(data)
                    adapter.notifyDataSetChanged()
                }

            }
        }

        viewModel.addAnswerLD.observe(this) { result ->
            result.onSuccess {
                viewModel.answerList.add(0, answer)
                adapter.notifyDataSetChanged()
                binding.nsContent.scrollTo(0, binding.tvQuestionDetailcontent.top)
                binding.tvAnswerNum.text = viewModel.answerList.size.toString()
                "添加回答成功！".showToast()
            }
            result.onFailure {
                "添加回答失败！".showToast()
            }
            loadingDialog.dismiss()
        }
    }


    override fun initView() {
        setToolbar(binding.tlQuestionDetail)
        adapter = AnswerAdapter(viewModel.answerList, this)
        binding.rvAnswer.layoutManager = LinearLayoutManager(this)
        binding.rvAnswer.adapter = adapter

        question = intent.parcelable("question")
        question?.let {
            binding.tvQuestionDetailUserName.text = it.user.name
            binding.tvQuestionDetailDate.text = formatDateTime(it.createdAt)
            binding.tvQuestionDetailcontent.text = it.content
            useGlideSetImage(this, FzpServiceCreator.getNetworkImageURL(it.user.avatar), binding.ivQuestionDetailUserAvatar)
            binding.tvAnswerNum.text = it.answerNum.toString()
            LogUtil.d(this, it.toString())
            LogUtil.d(this, it.id.toString())
            viewModel.getAnswerList(it.id)
        }
    }

    override fun initListener() {
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
        binding.etCommentContent.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                UserContext.comment(this) {}
            }
        }
        //发送回答
        binding.btnCommentSend.setOnClickListener {
            val content = binding.etCommentContent.text.toString().trim()
            val user = viewModel.getUserInfo()
            hideKeyboard(binding.etCommentContent.windowToken)
            binding.etCommentContent.setText("")
            loadingDialog = LoadingDialog(this, "正在发送")
            loadingDialog.show()
            answer = Answer(0, content, Date(), question!!.id, user)
            viewModel.addAnswer(answer)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.etCommentContent.clearFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
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
        }
        return false
    }

}