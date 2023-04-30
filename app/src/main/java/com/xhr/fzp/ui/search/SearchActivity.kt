package com.xhr.fzp.ui.search

import android.view.MenuItem
import com.xhr.fzp.R
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivitySearchBinding
import com.xhr.fzp.utils.replaceFragment
import com.xhr.fzp.utils.setToolbar

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override fun initView() {
        setToolbar(binding.tbSearch, "搜索")
        replaceFragment(R.id.fl_search_fragment, SearchFragment())
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