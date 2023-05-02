package com.xhr.fzp.ui.topictest

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseActivity
import com.xhr.fzp.databinding.ActivityPersonalTestBinding
import com.xhr.fzp.utils.setToolbar

class PersonalTestActivity : BaseActivity<ActivityPersonalTestBinding>() {

    val viewModel by lazy { ViewModelProvider(this)[PersonalTestViewModel::class.java] }
    private lateinit var adapter : PersonalTestAdapter
    override fun initData() {
        viewModel.userTopicListLD.observe(this) { result ->
            result.onSuccess {
                viewModel.userTopicList.clear()
                viewModel.userTopicList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun initView() {
        setToolbar(binding.tbPersonalTest, "自测情况")
        adapter = PersonalTestAdapter(this, viewModel.userTopicList)
        binding.rvPersonalTest.adapter = adapter
        binding.rvPersonalTest.layoutManager = LinearLayoutManager(this)
        val user = viewModel.getUserInfo()
        viewModel.getUserTopic(user.id)
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