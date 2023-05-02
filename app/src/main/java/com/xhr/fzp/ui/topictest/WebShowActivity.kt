package com.xhr.fzp.ui.topictest

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityStartTestBinding
import com.xhr.fzp.utils.setToolbar

class WebShowActivity : BaseActivity<ActivityStartTestBinding>() {

    companion object {
        fun actionStart(context: Context, url: String, title: String) {
            val intent = Intent(context, WebShowActivity::class.java).apply {
                putExtra("url", url)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }

    }

    override fun initView() {
        val url = intent.getStringExtra("url")!!
        val title = intent.getStringExtra("title")!!

        setToolbar(binding.tbStartTest, title)
        binding.wvStartTest.webChromeClient = object : WebChromeClient() {
            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
            }
        }
        binding.wvStartTest.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // 在当前 WebView 中加载 URL
                view.loadUrl(url)
                return true
            }
        }
        binding.wvStartTest.settings.javaScriptEnabled = true
        binding.wvStartTest.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}