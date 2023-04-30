package com.xhr.fzp.ui.question

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Question

class QuestionViewModel : ViewModel() {
    val questionList = ArrayList<Question>()

    private val getQuestionListLD = MutableLiveData<Pair<String, Int>>()
    val questionListLD = getQuestionListLD.switchMap { result ->
        Repository.getQuestionList(result.first, result.second)
    }

    fun getQuestionList(userId: String, review: Int) {
        getQuestionListLD.value = Pair(userId, review)
    }

}
