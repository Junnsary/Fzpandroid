package com.xhr.fzp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.xhr.fzp.utils.LogUtil
import com.xhr.fzp.utils.ViewBindingUtil

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        LogUtil.d(this, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        LogUtil.d(this, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.d(this, "onViewCreated")
        initView()
        initListener()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        LogUtil.d(this, "onViewStateRestored")
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(this, "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d(this, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(this, "onDestroy")
    }


    protected open fun initData(){}
    protected open fun initView(){}
    protected open fun initListener(){}

}