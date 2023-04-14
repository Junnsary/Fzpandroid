package com.xhr.fzp.ui.knowledge.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.interfaces.IDbData
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.logic.room.entity.Save

class RecommViewModel : ViewModel() , IDbData {

    val recommList = ArrayList<Source>()

    private val getRecommListLD = MutableLiveData<Int>()
    val recommListLD = getRecommListLD.switchMap{ result ->
        Repository.getRecommList(result)
    }

    fun getRecommList(num: Int) {
        getRecommListLD.value = num
    }

    override fun saveSourceToDatabase(saves: List<Save>) {
        Repository.saveSourceToDatabase(saves)
    }

    override fun getSourceFromDatabase(category: String, name: String, type: Array<String>): LiveData<List<Save>> {
        return Repository.getSourceFromDatabase(category, name, type)
    }
}