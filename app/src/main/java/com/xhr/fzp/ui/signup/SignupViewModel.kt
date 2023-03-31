package com.xhr.fzp.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.User

class SignupViewModel : ViewModel() {

    private val getSignupLD = MutableLiveData<User>()
    val signupLD = getSignupLD.switchMap { result ->
        Repository.userSignup(result)
    }

    fun userSignup(user: User) {
        getSignupLD.value = user
    }


}