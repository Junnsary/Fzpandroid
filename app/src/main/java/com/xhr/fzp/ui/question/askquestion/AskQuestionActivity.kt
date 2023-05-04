package com.xhr.fzp.ui.question.askquestion

import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityAskQuestionBinding
import com.xhr.fzp.logic.model.Question
import com.xhr.fzp.utils.LoadingDialog
import com.xhr.fzp.utils.setToolbar
import com.xhr.fzp.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AskQuestionActivity : BaseActivity<ActivityAskQuestionBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[AskQuestionViewModel::class.java] }
    private var sendFlag = true
    private lateinit var loadingDialog: LoadingDialog
    override fun initData() {
        viewModel.sendAskQuestionLD.observe(this) {
            it.onSuccess {
                "发送成功！请等待审核通过".showToast()
                binding.etQuestionContent.setText("")
                finish()
            }
            it.onFailure {
                "发送失败！请重试".showToast()
            }
            loadingDialog.dismiss()
            sendFlag = true
        }
    }

    override fun initView() {
        setToolbar(binding.tbAskQuestion, "提问")
        binding.etQuestionContent.requestFocus()
    }

    override fun initListener() {
        binding.tbAskQuestion.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_ask_question -> {
                    if (sendFlag) {
                        sendQuestion()
                    }
                }
            }
            true
        }
    }

    private fun sendQuestion() {
        val questionContent = binding.etQuestionContent.text.toString().trim()
        if (questionContent.isNotEmpty()) {
            loadingDialog = LoadingDialog(this, "正在发送...")
            loadingDialog.show()
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                val user = viewModel.getUserInfo()
                viewModel.sendAskQuestion(Question(questionContent,  user))
            }
        }
        sendFlag = false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_ask_question, menu)
        return true
    }

}