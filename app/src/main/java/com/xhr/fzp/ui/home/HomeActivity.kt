package com.xhr.fzp.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityHomeBinding
import com.xhr.fzp.ui.case.CaseFragment
import com.xhr.fzp.ui.knowledge.KnowledgeFragment
import com.xhr.fzp.ui.mime.MimeFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    val fragments = arrayOf(KnowledgeFragment(), CaseFragment(), MimeFragment())
    override fun initView() {
//        binding.bnvHome.itemIconTintList = null
        binding.vpHome.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }
            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        binding.vpHome.isUserInputEnabled = false
        binding.vpHome.offscreenPageLimit = 1

        binding.tbHome.title = "学习区"
        binding.bnvHome.setOnItemSelectedListener { v ->
            when (v.itemId) {
                R.id.menu_item_study -> {
                    binding.vpHome.setCurrentItem(0, false)
                    binding.tbHome.title = "学习区"
                    binding.tbHome.elevation = 0f
                }
                R.id.menu_item_case -> {
                    binding.vpHome.setCurrentItem(1, false)
                    binding.tbHome.title = "案例区"
                    binding.tbHome.elevation = 0f
                }
                R.id.menu_item_mime -> {
                    binding.vpHome.setCurrentItem(2, false)
                    binding.tbHome.title = "我的"
                    binding.tbHome.elevation = 5f
                }
            }
            true
        }
    }
}