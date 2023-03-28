package com.xhr.fzp.ui.mime

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentMimeBinding
import com.xhr.fzp.mode.state.UserContext
import com.xhr.fzp.utils.LogUtil

class MimeFragment :  BaseFragment<FragmentMimeBinding>(){

    val viewModel by lazy { ViewModelProvider(this)[MimeViewModel::class.java]}
    private var isLogin = false
    override fun initData() {
        // 检查是否登录了，存了用户的信息
        //网络获取头像
        viewModel.avatarLD.observe(this) {
            val body = it.getOrNull()
            if (body != null) {
                val avatar = body.byteStream()
                //设置头像
                val bitmap = BitmapFactory.decodeStream(avatar)
                binding.ivAvatar.setImageBitmap(bitmap)
                //保存到本地去
                viewModel.saveUserAvatarToLocal(bitmap)
            }
        }

    }

    override fun initView() {
        if (viewModel.isUserLogin()) {
            LogUtil.d(this, "有用户！")
            setLoginInfo()
        } else {
            LogUtil.d(this, "没有用户！")
        }

        binding.ivAvatar.setOnClickListener {
            UserContext.login(activity) {}
        }
    }

    private fun setLoginInfo() {
        isLogin = true
        //获取用户数据
        val user = viewModel.getSavedUser()
        LogUtil.d(this, user.toString())
        binding.tvUserName.text = user.name
        /**
         * 获取头像
         * 1. 首先查看本地是否有
         *  有：读取图片，并且设置图片
         *  没有：获取网络图片，设置，然后保存在本地图片
         */
        if (viewModel.isLocalAvatar()) {
            val localAvatar = viewModel.getLocalAvatar()
            binding.ivAvatar.setImageBitmap(localAvatar)
        } else {
            viewModel.getNetAvatar(user.avatar)
        }
    }

    override fun onStart() {
        super.onStart()
        //如果前面在创建的时候 已经登录过了，就不操作了，如果没有就是说
        if (!isLogin && viewModel.isUserLogin()) {
            setLoginInfo()
        }
    }


}