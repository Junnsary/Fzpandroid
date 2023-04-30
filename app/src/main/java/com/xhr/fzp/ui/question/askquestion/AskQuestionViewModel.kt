package com.xhr.fzp.ui.question.askquestion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Question

class AskQuestionViewModel: ViewModel() {
    private val getSendAskQuestionLD = MutableLiveData<Question>()
    val sendAskQuestionLD = getSendAskQuestionLD.switchMap { result ->
        Repository.sendAskQuestion(result)
    }

    fun sendAskQuestion(question: Question) {
        getSendAskQuestionLD.value = question
    }

    fun getUserInfo() = Repository.getSavedUser()
}
