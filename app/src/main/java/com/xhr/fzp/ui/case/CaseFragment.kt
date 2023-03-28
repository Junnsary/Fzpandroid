package com.xhr.fzp.ui.case

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentCaseBinding
import com.xhr.fzp.ui.info.InfoFragment
import com.xhr.fzp.utils.replaceFragment

class CaseFragment :  BaseFragment<FragmentCaseBinding>(){

    val viewModel by lazy { ViewModelProvider(this)[CaseViewModel::class.java]}

    override fun initView() {
        val infoFragment = InfoFragment.getVideoCase(true)
        replaceFragment(R.id.fl_video_case, infoFragment)
    }

}