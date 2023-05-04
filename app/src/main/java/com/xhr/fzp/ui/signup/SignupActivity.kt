package com.xhr.fzp.ui.signup

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySignupBinding
import com.xhr.fzp.logic.model.InputNumFilter
import com.xhr.fzp.logic.model.SpaceFilter
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.utils.LoadingDialog
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.createDialog
import com.xhr.fzp.utils.isEmailFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignupActivity : BaseActivity<ActivitySignupBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[SignupViewModel::class.java] }
    private lateinit var dialog: androidx.appcompat.app.AlertDialog
    private lateinit var signupLoadingDialog: LoadingDialog
    override fun initData() {
        viewModel.signupLD.observe(this) { result ->
            val succ = result.getOrNull()

            CoroutineScope(Dispatchers.Main).launch{
                delay(1000)
                loginLoadingClose()
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

    }

    override fun initView() {
        setSupportActionBar(binding.tlSignup)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }
        binding.etSignupUserEmail.filters = arrayOf(SpaceFilter())
        binding.etSignUserId.filters = arrayOf(SpaceFilter(), InputNumFilter(10))
        binding.etSignupUserName.filters = arrayOf(SpaceFilter(), InputNumFilter(10))
        binding.etSignupUserPasswd.filters = arrayOf(SpaceFilter(), InputNumFilter(16))
        binding.etSignupConfirmPasswd.filters = arrayOf(SpaceFilter(), InputNumFilter(16))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initListener() {
        binding.btnSignup.setOnClickListener {
            val verify = ArrayList<String>()
            val email = binding.etSignupUserEmail.text.toString()
            val id = binding.etSignUserId.text.toString()
            val name = binding.etSignupUserName.text.toString()
            val passwd = binding.etSignupUserPasswd.text.toString()
            val confirmPasswd = binding.etSignupConfirmPasswd.text.toString()

            if (!isEmailFormat(email)) {
                verify.add("邮箱格式不正确")
            }

            if (id.isEmpty()) {
                verify.add("请输入用户ID")
            }

            if (name.isEmpty()) {
                verify.add("请输入用户名称")
            }

            if (passwd.length < 8) {
                verify.add("密码长度不规范")
            } else {
                if (passwd != confirmPasswd){
                    verify.add("两次密码不正确")
                }
            }

            if (verify.isEmpty()) {
                loginLoadingShow()
                val user = User(id, email, name, passwd, "")
                viewModel.userSignup(user)
            } else {
                val sb = StringBuilder()
                verify.forEach {
                    sb.append("${it}\n")
                }
                showDialog(sb.toString())
            }
        }

        /**
         * public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
        }
         */
    }

    private fun loginLoadingShow() {
        signupLoadingDialog = LoadingDialog(this, "注册中...")
        signupLoadingDialog.show()
    }

    private fun loginLoadingClose() {
        signupLoadingDialog.dismiss()
    }

    //验证邮箱格式


    private fun showDialog(msg: String) {
        dialog = createDialog(msg)
    }

    private fun closeDialog() {
        dialog.dismiss()
    }

    private fun resetInput() {
        binding.etSignupUserEmail.requestFocus()
        binding.etSignUserId.setText("")
        binding.etSignupUserEmail.setText("")
        binding.etSignupConfirmPasswd.setText("")
        binding.etSignupUserPasswd.setText("")
        binding.etSignupUserName.setText("")
    }
}