package com.xhr.fzp.ui.topictest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.UserTopic

class PersonalTestViewModel : ViewModel() {
    val userTopicList = ArrayList<UserTopic>()

    private val getUserTopicListLD = MutableLiveData<String>()
    val userTopicListLD = getUserTopicListLD.switchMap { result ->
        Repository.getUserTopicList(result)
    }

    fun getUserTopic(userId: String) {
        getUserTopicListLD.value = userId
    }

    fun getUserInfo() = Repository.getSavedUser()
}
