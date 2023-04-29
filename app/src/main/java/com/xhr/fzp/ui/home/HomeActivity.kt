package com.xhr.fzp.ui.home

import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityHomeBinding
import com.xhr.fzp.ui.case.CaseFragment
import com.xhr.fzp.ui.knowledge.KnowledgeFragment
import com.xhr.fzp.ui.mime.MimeFragment
import com.xhr.fzp.ui.search.SearchFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    val fragments = arrayOf(KnowledgeFragment(), CaseFragment(),SearchFragment(), MimeFragment())
    override fun initView() {
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

        binding.tbHome.title = getString(R.string.activity_mian_study_str)
        binding.bnvHome.setOnItemSelectedListener { v ->
            when (v.itemId) {
                R.id.menu_item_study -> {
                    binding.vpHome.setCurrentItem(0, false)
                    binding.tbHome.title = getString(R.string.activity_mian_study_str)
                    binding.tbHome.elevation = 0f
                }
                R.id.menu_item_case -> {
                    binding.vpHome.setCurrentItem(1, false)
                    binding.tbHome.title = getString(R.string.activity_main_case_str)
                    binding.tbHome.elevation = 0f
                }
                R.id.menu_item_search -> {
                    binding.vpHome.setCurrentItem(2, false)
                    binding.tbHome.title = getString(R.string.activity_main_search_str)
                    binding.tbHome.elevation = 0f
                }
                R.id.menu_item_mime -> {
                    binding.vpHome.setCurrentItem(3, false)
                    binding.tbHome.title = getString(R.string.activity_main_mine_str)
                    binding.tbHome.elevation = 5f
                }

            }
            true
        }

        val viewTreeObserver = binding.vpHome.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                binding.vpHome.viewTreeObserver.removeOnGlobalLayoutListener(this)
                /**
                 * int pagerHeight = mViewPager.getHeight();
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mViewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
                linearParams.height = pagerHeight;// 当控件的高强制设成测量好的高
                linearParams.weight = 0;//
                mViewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
                 */
                val pagerHeight: Int = binding.vpHome.height
                val linearParams = binding.vpHome.layoutParams as LinearLayout.LayoutParams // 取控件mGrid当前的布局参数

                linearParams.height = pagerHeight // 当控件的高强制设成测量好的高

                linearParams.weight = 0f //

                binding.vpHome.layoutParams = linearParams // 使设置好的布局参数应用到控件

            }

        })

    }
}