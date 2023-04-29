package com.xhr.fzp.ui.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityLoginBinding
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.utils.state.UserContext
import com.xhr.fzp.ui.signup.SignupActivity
import com.xhr.fzp.utils.LoadingDialog
import com.xhr.fzp.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private lateinit var loginLoadingDialog: LoadingDialog
    private var isUserIdInput = false
    private var isUserPasswdInput = false
    override fun initData() {

        viewModel.userLoginLD.observe(this) { result ->
            val user = result.getOrNull()
            if (user != null) {
                CoroutineScope(Dispatchers.Main).launch{
                    delay(1000)
                    loginLoadingClose()
                    if (user.success) {
                        "登录成功".showToast()
                        //这里保存信息
                        viewModel.saveUser(user.data)
                        UserContext.setLoginState()
                        finish()
                    } else {
                        "登录失败，ID或者错误".showToast()
                    }
                }
            }
        }
    }

    override fun initView() {
        setSupportActionBar(binding.tlLogin)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }
        notAllowLoginBtn()
    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener {
            loginLoadingShow()
            val id = binding.etUserId.text.toString()
            val passwd = binding.etUserPasswd.text.toString()
            viewModel.userLogin(User(id, "", "", passwd, ""))
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.etUserId.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                isUserIdInput = s.toString().isNotEmpty()
                if (isUserIdInput && isUserPasswdInput) {
                    allowLoginBtn()
                } else {
                    notAllowLoginBtn()
                }
            }

        })
        binding.etUserPasswd.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                isUserPasswdInput = s.toString().isNotEmpty()
                if (isUserIdInput && isUserPasswdInput) {
                    allowLoginBtn()
                } else {
                    notAllowLoginBtn()
                }
            }
        })
    }

    private fun loginLoadingShow() {
        loginLoadingDialog = LoadingDialog(this, "登录中...")
        loginLoadingDialog.show()
    }

    private fun loginLoadingClose() {
        loginLoadingDialog.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun allowLoginBtn() {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setTextColor(getColor(R.color.theme_white_fa))
    }

    private fun notAllowLoginBtn() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.setTextColor(getColor(R.color.gray_text_color))
    }
}