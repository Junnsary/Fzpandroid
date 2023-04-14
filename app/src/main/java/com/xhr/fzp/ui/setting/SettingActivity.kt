package com.xhr.fzp.ui.setting

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySettingBinding
import com.xhr.fzp.mode.state.UserContext
import com.xhr.fzp.utils.setToolbar

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[SettingViewModel::class.java] }

    override fun initView() {
        setToolbar(binding.tlSetting, "设置")
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