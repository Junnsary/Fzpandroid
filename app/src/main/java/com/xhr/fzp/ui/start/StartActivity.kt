package com.xhr.fzp.ui.start

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityStartBinding
import com.xhr.fzp.ui.home.HomeActivity
import com.xhr.fzp.utils.quickStartActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(0)
            quickStartActivity<HomeActivity>()
            finish()
        }
    }
}