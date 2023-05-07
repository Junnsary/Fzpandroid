package com.xhr.fzp.ui.home

import android.view.Menu
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
import com.xhr.fzp.ui.question.QuestionFragment
import com.xhr.fzp.ui.question.askquestion.AskQuestionActivity
import com.xhr.fzp.ui.search.SearchActivity
import com.xhr.fzp.ui.topictest.TopicTestFragment
import com.xhr.fzp.utils.quickStartActivity
import com.xhr.fzp.utils.state.UserContext

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    val fragments = arrayOf(KnowledgeFragment(), CaseFragment(),QuestionFragment("", 1), TopicTestFragment(), MimeFragment())
    override fun initView() {
        setSupportActionBar(binding.tbHome)
        supportActionBar?.let {
            it.title = "学习"
        }
        binding.vpHome.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }

        binding.vpHome.isUserInputEnabled = false
        binding.vpHome.offscreenPageLimit = 4
        binding.bnvHome.setOnItemSelectedListener { v ->
            when (v.itemId) {
                R.id.menu_item_study -> {
                    binding.vpHome.setCurrentItem(0, false)
                    binding.tbHome.title = getString(R.string.activity_mian_study_str)
                    binding.tbHome.elevation = 0f
                    binding.tbHome.menu.findItem(R.id.action_search).isVisible = true
                    binding.tbHome.menu.findItem(R.id.action_question).isVisible = false
                }
                R.id.menu_item_case -> {
                    binding.vpHome.setCurrentItem(1, false)
                    binding.tbHome.title = getString(R.string.activity_main_case_str)
                    binding.tbHome.elevation = 0f
                    binding.tbHome.menu.findItem(R.id.action_search).isVisible = true
                    binding.tbHome.menu.findItem(R.id.action_question).isVisible = false
                }
                R.id.menu_item_search -> {
                    binding.vpHome.setCurrentItem(2, false)
                    binding.tbHome.title = getString(R.string.menu_home_bnv_qa_str)
                    binding.tbHome.elevation = 5f
                    binding.tbHome.menu.findItem(R.id.action_search).isVisible = false
                    binding.tbHome.menu.findItem(R.id.action_question).isVisible = true
                }
                R.id.menu_item_topic_test -> {
                    binding.vpHome.setCurrentItem(3, false)
                    binding.tbHome.title = getString(R.string.menu_home_bnv_test_str)
                    binding.tbHome.elevation = 5f
                    binding.tbHome.menu.findItem(R.id.action_search).isVisible = false
                    binding.tbHome.menu.findItem(R.id.action_question).isVisible = false
                }
                R.id.menu_item_mime -> {
                    binding.vpHome.setCurrentItem(4, false)
                    binding.tbHome.title = getString(R.string.activity_main_mine_str)
                    binding.tbHome.elevation = 5f
                    binding.tbHome.menu.findItem(R.id.action_search).isVisible = false
                    binding.tbHome.menu.findItem(R.id.action_question).isVisible = false
                }
            }
            true
        }

        val viewTreeObserver = binding.vpHome.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
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
                val linearParams =
                    binding.vpHome.layoutParams as LinearLayout.LayoutParams // 取控件mGrid当前的布局参数
                linearParams.height = pagerHeight // 当控件的高强制设成测量好的高
                linearParams.weight = 0f //
                binding.vpHome.layoutParams = linearParams // 使设置好的布局参数应用到控件
            }
        })

        //设置搜索的监听时间
        binding.tbHome.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    quickStartActivity<SearchActivity>()
                }
                R.id.action_question -> {
                    UserContext.login(this){
                        quickStartActivity<AskQuestionActivity>()
                    }
                }
            }
            true
        }

    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_home, menu)
        menu.findItem(R.id.action_question).isVisible = false
        return true
    }
}