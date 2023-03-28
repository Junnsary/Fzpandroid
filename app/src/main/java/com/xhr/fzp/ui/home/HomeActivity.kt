package com.xhr.fzp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

//    private val knowledgeFragment = KnowledgeFragment()
//    private val caseFragment = CaseFragment()
//    private val mimeFragment = MimeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
//        switchHomeFragment(viewModel.knowledgeFragment)
        binding.vpHome.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return viewModel.fragments.size
            }
            override fun createFragment(position: Int): Fragment {
                return viewModel.fragments[position]
            }
        }
        binding.vpHome.isUserInputEnabled = false
        binding.vpHome.offscreenPageLimit = 1

        binding.bnvHome.setOnItemSelectedListener { v ->
//            when (v.itemId) {
//                R.id.menu_item_study -> switchHomeFragment(viewModel.knowledgeFragment)
//                R.id.menu_item_case -> switchHomeFragment(viewModel.caseFragment)
//                R.id.menu_item_mime -> switchHomeFragment(viewModel.mimeFragment)
//            }
            when (v.itemId) {
                R.id.menu_item_study -> binding.vpHome.setCurrentItem(0, false)
                R.id.menu_item_case -> binding.vpHome.setCurrentItem(1, false)
                R.id.menu_item_mime -> binding.vpHome.setCurrentItem(2, false)
            }
            true
        }
    }

    private fun switchHomeFragment(fragment: Fragment) {
//        val sfm = supportFragmentManager
//        val bt = sfm.beginTransaction()
//        bt.replace(R.id.fl_home, fragment)
//        bt.commit()
    }

}