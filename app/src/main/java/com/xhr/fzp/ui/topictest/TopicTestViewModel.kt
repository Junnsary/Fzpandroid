package com.xhr.fzp.ui.topictest

import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class TopicTestViewModel : ViewModel() {
    fun getUserInfo() = Repository.getSavedUser()
}
