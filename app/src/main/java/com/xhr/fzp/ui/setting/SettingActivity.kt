package com.xhr.fzp.ui.setting

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySettingBinding
import com.xhr.fzp.mode.state.UserContext

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[SettingViewModel::class.java] }

    override fun initView() {

    }

    override fun initListener() {
        binding.btnLogout.setOnClickListener {
            /**
             * 退出登录
             * 1. 清除账号的登录
             * 2. 清除账号的头像
             * 3. UserContext设置成 Logout
             */
            viewModel.clearUser()
            viewModel.clearLocalUserAvatar()
            UserContext.setLogoutState()
            finish()
        }
    }

}