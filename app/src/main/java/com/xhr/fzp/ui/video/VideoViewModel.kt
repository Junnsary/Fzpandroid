package com.xhr.fzp.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class VideoViewModel : ViewModel() {

    private val getVideoInfoLD = MutableLiveData<Int>()
    val videoInfoLD = Transformations.switchMap(getVideoInfoLD) { result ->
        Repository.getVideoInfo(result)
    }

    fun getVideoInfo(sourceId: Int) {
        getVideoInfoLD.value = sourceId
    }

}
