package com.xhr.fzp.ui.source

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Source
import com.xhr.fzp.logic.model.Tag

class SourceViewModel : ViewModel() {
    val sourceList = ArrayList<Source>()

    private val getSourceListLD= MutableLiveData<Pair<String, String>>()
    val sourceListLD = Transformations.switchMap(getSourceListLD) { result ->
        Repository.getSourceList(result.first, result.second)
    }

    fun getSourceList(tags: List<Tag>){
        var tb = StringBuilder()
        for (tag in tags) {
            tb.append(tag.id.toString())
            if (tag != tags[tags.size - 1]) {
                tb.append(",")
            }
        }
        getSourceListLD.value = Pair(tags[0].type, tb.toString())
    }
}