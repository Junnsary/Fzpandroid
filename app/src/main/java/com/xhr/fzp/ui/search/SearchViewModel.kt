package com.xhr.fzp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Source
import java.security.Key

class SearchViewModel : ViewModel(){
    val searchList =  ArrayList<Source>()

    private val getSearchListLD = MutableLiveData<String>()
    val searchListLD = getSearchListLD.switchMap { result ->
        Repository.getSearchList(result)
    }

    fun getSearchList(keywords: String) {
        getSearchListLD.value = keywords
    }
}
