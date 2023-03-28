package com.xhr.fzp.ui.login

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityLoginBinding
import com.xhr.fzp.logic.model.User
import com.xhr.fzp.mode.state.UserContext
import com.xhr.fzp.ui.signup.SignupActivity
import com.xhr.fzp.utils.createDialog
import com.xhr.fzp.utils.showToast

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }
    private lateinit var loadingDialog: AlertDialog
    override fun initData() {
//        viewModel.AvatarLD.observe(this){ result ->
//            val avatar = result.getOrNull()
//            if (avatar != null) {
////                LogUtil.d(this, avatar.bytes().size.toString())
////                LogUtil.d(this, avatar.contentLength().toInt().toString())
////                val t = avatar.bytes().clone()
////                avatar.bytes().
//                val img = BitmapFactory.decodeStream(avatar.byteStream())
//                binding.imageView.setImageBitmap(img)
//            } else {
//                LogUtil.d(this, "接收不到")
//            }
//        }

        viewModel.userLoginLD.observe(this) { result ->
            val user = result.getOrNull()
            if (user != null) {
                if (user.success) {
//                    LogUtil.d(this, user.toString())
//                    LogUtil.d(this, user.data.toString())
                    "登录成功".showToast()
//                    val imageUrl = "http://10.0.2.2:3000/uploads/images/avatars/image2.jpg"
//                    Glide.with(this)
//                        .load(imageUrl)
//                        .listener(object :  RequestListener<Drawable> {
//                            override fun onLoadFailed(
//                                e: GlideException?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                isFirstResource: Boolean
//                            ): Boolean {
//                                LogUtil.d(this,"加载失败 errorMsg:"+(e?:e?.message));
//                                return false
//                            }
//
//                            override fun onResourceReady(
//                                resource: Drawable?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                dataSource: DataSource?,
//                                isFirstResource: Boolean
//                            ): Boolean {
//                                LogUtil.d(this, "成功  Drawable Name:")
//                                return false
//                            }
//                        })
//                        .error(R.mipmap.ic_launcher)
//                        .into(binding.imageView)
//                    viewModel.getAvatar(user.data.avatar)
                    //这里保存信息
                    viewModel.saveUser(user.data)
                    UserContext.setLoginState()
                    finish()
                } else {
                    "登录失败".showToast()
                }
            }
            closeDialog()
        }
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener {
            showDialog()
            val id = binding.etUserId.text.toString()
            val passwd = binding.etUserPasswd.text.toString()
//            LogUtil.d(this, binding.etUserId.text.toString())
//            LogUtil.d(this, binding.etUserPasswd.text.toString())
            viewModel.userLogin(User(id, "", "", passwd, ""))
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.tvReturn.setOnClickListener {
            finish()
        }
    }

    private fun showDialog() {
        loadingDialog = createDialog("正在登录...")
    }

    private fun closeDialog() {
        loadingDialog.dismiss()
    }
}