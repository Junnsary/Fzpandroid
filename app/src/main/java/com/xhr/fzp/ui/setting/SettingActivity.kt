package com.xhr.fzp.ui.setting

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySettingBinding
import com.xhr.fzp.utils.*
import com.xhr.fzp.utils.state.UserContext

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[SettingViewModel::class.java] }
    private lateinit var dialogExplain : MessageDialog
    override fun initView() {
        setToolbar(binding.tlSetting, "设置")
        showCacheDirSize()
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

        binding.mcvPersonalExplain.setOnClickListener {
            showDialogExplain()
        }
        binding.mcvClearCache.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("提示")
                setMessage("确认清除缓存吗？")
                setPositiveButton("确定") { dialog, which ->
                    val cacheDir = externalCacheDir
                    if (cacheDir != null && cacheDir.isDirectory) {
                        for (child in cacheDir.listFiles()!!) {
                            if (child.isDirectory) {
                                deleteRecursive(child)
                            } else {
                                child.delete()
                            }
                        }
                    }
                    showCacheDirSize()
                }
                setNegativeButton("取消"){_,_->}
                show()
            }
        }
    }

    private fun showDialogExplain() {
        dialogExplain = MessageDialog(this, "开发者：信息技术工程学院 计科1813 萧宏润\n" +
                "这款《大学生校园防诈骗知识交流App》软件是作为2023毕业设计的作品。")
        dialogExplain.setCanceledOnTouchOutside(true)
        dialogExplain.show()
    }

    private fun showCacheDirSize() {
        val cacheFile = externalCacheDir
        val size = getFolderSize(cacheFile!!)
        binding.tvCacheSize.text = "清除缓存 (${formatFileSize(size)})"
    }
}