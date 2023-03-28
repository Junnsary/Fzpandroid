package com.xhr.fzp.ui.knowledge.recommendation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Source

class RecommViewModel : ViewModel() {

    val recommList = ArrayList<Source>()

    private val getRecommListLD = MutableLiveData<Int>()
    val recommListLD = Transformations.switchMap(getRecommListLD){ result ->
        Repository.getRecommList(result)
    }

    fun getRecommList(num: Int) {
        getRecommListLD.value = num
    }
}