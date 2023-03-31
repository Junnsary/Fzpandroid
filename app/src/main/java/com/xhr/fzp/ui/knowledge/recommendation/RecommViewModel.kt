package com.xhr.fzp.ui.knowledge.recommendation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Source

class RecommViewModel : ViewModel() {

    val recommList = ArrayList<Source>()

    private val getRecommListLD = MutableLiveData<Int>()
    val recommListLD = getRecommListLD.switchMap{ result ->
        Repository.getRecommList(result)
    }

    fun getRecommList(num: Int) {
        getRecommListLD.value = num
    }
}