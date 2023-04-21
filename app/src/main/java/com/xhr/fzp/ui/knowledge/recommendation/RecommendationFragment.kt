package com.xhr.fzp.ui.knowledge.recommendation

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xhr.fzp.base.BaseFragment
import com.xhr.fzp.databinding.FragmentRecommBinding
import com.xhr.fzp.logic.interfaces.IRefresh
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.ui.source.SourceAdapter
import com.xhr.fzp.utils.LogUtil

class RecommendationFragment : BaseFragment<FragmentRecommBinding>(), IRefresh {

    val viewModel by lazy { ViewModelProvider(this)[RecommendationViewModel::class.java] }

    private lateinit var adapter: SourceAdapter

    override fun initData() {
        viewModel.recommListLD.observe(this) { result ->
//            LogUtil.d(this, "推荐接受不到")
            result.onSuccess {
                //获取到数据
                if (it.isNotEmpty()) {
                    updateSource(it)
                    it.forEach { element ->
                        LogUtil.d(
                            this, element.id.toString()
                        )
                    }
                    /**
                     * 之前需要先删除保存的数据和图片
                     *
                     * 保存下来
                     * 1. 保存数据
                     * 2. 在adapter里保存缓存图片
                     * 3. 先删除再保存新的数据
                     */
//                viewModel.saveSourceToDatabase(dataTurnToSave(data, "knowledge", "推荐"))
                } else {
                    LogUtil.d(this, "获取不到数据")
                }
            }
            result.onFailure {
                showConnectError()
            }

            stopRefresh()
        }

//        viewModel.recommListLD.observeResult(this, {
//
//        }, {
//            if (it is ConnectException) {
//                LogUtil.d(this, "网络发生错误！！！")
//            }
//        })
    }

    override fun initView() {
        adapter = SourceAdapter(this, viewModel.recommList)
        binding.rvRecommendation.layoutManager = LinearLayoutManager(activity)
        binding.rvRecommendation.adapter = adapter
        refresh()
//        viewModel.getSourceFromDatabase("knowledge", "推荐", arrayOf("article", "video")).observe(this) { result ->
//            //存储在数据库的
//            if (result != null && result.isNotEmpty()) {
//                val temp = saveTurnToData(result)
//                updateSource(temp)
//            } else {
//                //没有就刷新
//                refresh()
//            }
//        }
    }

    private fun updateSource(data: List<Source>) {
        viewModel.recommList.clear()
        viewModel.recommList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun initListener() {
        binding.srlRefreshData.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh() {
        closeConnectError()
        binding.srlRefreshData.isRefreshing = true
        viewModel.getRecommList(10)
    }

    override fun stopRefresh() {
        binding.srlRefreshData.isRefreshing = false
    }

    private fun showConnectError() {
        binding.tvFailure.visibility = View.VISIBLE
    }

    private fun closeConnectError() {
        binding.tvFailure.visibility = View.GONE
    }
}