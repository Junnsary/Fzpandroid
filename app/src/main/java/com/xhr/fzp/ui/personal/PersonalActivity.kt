package com.xhr.fzp.ui.personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityPersonalBinding
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.setToolbar

class PersonalActivity : BaseActivity<ActivityPersonalBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[PersonalViewModel::class.java] }

    override fun initView() {
        setToolbar(binding.tlPersonal, "个人信息")
        //设置个人信息
        setUserInfo()
    }

    private fun setUserInfo() {
        val user = viewModel.getSavedUser()
        LogUtil.d(this, user.toString())
        binding.tvUserName.text = user.name
        binding.tvUserId.text = user.id
        binding.tvUserEmail.text = user.email
        val localAvatar = viewModel.getLocalAvatar()
        binding.ivAvatar.setImageBitmap(localAvatar)
    }

    override fun initListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

}