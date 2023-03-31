package com.xhr.fzp.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository

class VideoViewModel : ViewModel() {

    private val getVideoInfoLD = MutableLiveData<Int>()
    val videoInfoLD = getVideoInfoLD.switchMap { result ->
        Repository.getVideoInfo(result)
    }

    fun getVideoInfo(sourceId: Int) {
        getVideoInfoLD.value = sourceId
    }

}
