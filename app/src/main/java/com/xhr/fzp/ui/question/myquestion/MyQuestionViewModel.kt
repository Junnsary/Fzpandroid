package com.xhr.fzp.ui.question.myquestion

import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class MyQuestionViewModel : ViewModel() {
    fun getUserInfo() = Repository.getSavedUser()
}
