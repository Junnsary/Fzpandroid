package com.xhr.fzp.ui.question.questiondetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Answer

class QuestionDetailViewModel : ViewModel() {
    val answerList = ArrayList<Answer>()

    private val getAnswerListLD = MutableLiveData<Int>()
    val answerListLD = getAnswerListLD.switchMap { result ->
        Repository.getAnswerList(result)
    }

    fun getAnswerList(questionId: Int) {
        getAnswerListLD.value = questionId
    }


    private val getAddAnswerLD = MutableLiveData<Answer>()
    val addAnswerLD = getAddAnswerLD.switchMap { result ->
        Repository.addAnswer(result)
    }

    fun addAnswer(answer: Answer) {
        getAddAnswerLD.value = answer
    }

    fun getUserInfo() = Repository.getSavedUser()
}
