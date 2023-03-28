package com.xhr.fzp.ui.signup

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySignupBinding
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.createDialog

class SignupActivity : BaseActivity<ActivitySignupBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[SignupViewModel::class.java]}
    private lateinit var dialog: androidx.appcompat.app.AlertDialog
    override fun initData() {
        viewModel.signupLD.observe(this) { result ->
            val succ = result.getOrNull()
            if (succ != null) {
                LogUtil.d(this, succ.data.message)
                if (succ.success) {
                    showDialog("注册成功！")
                    resetInput()
                } else {
                    showDialog(succ.data.message)
                }
            }
        }
    }

    override fun initListener() {
        binding.btnSignup.setOnClickListener {
            val email = binding.etSignupUserEmail.text.toString().trim()
            val id = binding.etSignUserId.text.toString().trim()
            val name = binding.etSignupUserName.text.toString().trim()
            val passwd = binding.etSignupUserPasswd.text.toString().trim()
            val confirmPasswd = binding.etSignupConfirmPasswd.text.toString().trim()
            val user = User(id, email, name, passwd, "")
            viewModel.userSignup(user)
        }

        binding.tvReturn.setOnClickListener{
            finish()
        }
    }

    private fun showDialog(msg: String) {
        dialog = createDialog(msg)
    }

    private fun closeDialog() {
        dialog.dismiss()
    }

    private fun resetInput(){
        binding.etSignUserId.setText("")
        binding.etSignupUserEmail.setText("")
        binding.etSignupConfirmPasswd.setText("")
        binding.etSignupUserPasswd.setText("")
        binding.etSignupUserName.setText("")
    }
}