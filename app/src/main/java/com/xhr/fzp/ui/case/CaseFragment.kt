package com.xhr.fzp.ui.case

import androidx.lifecycle.ViewModelProvider
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentCaseBinding
import com.xhr.fzp.ui.info.InfoFragment
import com.xhr.fzp.utils.replaceFragment

class CaseFragment :  BaseFragment<FragmentCaseBinding>(){

    override fun initView() {
        val infoFragment = InfoFragment.getCase(true)
        replaceFragment(R.id.fl_video_case, infoFragment)
    }
}