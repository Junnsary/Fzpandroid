package com.xhr.fzp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.ViewBindingUtil

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
//        window.statusBarColor = getColor(R.color.my_black)

        LogUtil.d(this, "onCreate")
        initData()
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(this, "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(this, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(this, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(this, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d(this, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(this, "onDestroy")
    }

    open fun initData(){}
    open fun initView(){}
    open fun initListener(){
    }
}