package com.xhr.fzp.ui.info

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Tag

class InfoViewModel : ViewModel() {
    val tagList = ArrayList<Tag>()
    val fragments = ArrayList<Fragment>()
    val getTagListLD = MutableLiveData<Pair<String, String>>()
    val TagListLD = getTagListLD.switchMap { result ->
        Repository.getTags(result.first, result.second)
    }
    fun getTagList(type: String, category: String){
        getTagListLD.value = Pair(type, category)
    }

}