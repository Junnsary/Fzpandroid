package com.xhr.fzp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.User

class LoginViewModel : ViewModel() {

    private val getUserLoginLD = MutableLiveData<User>()
    val userLoginLD = getUserLoginLD.switchMap{ result ->
        Repository.userLogin(result)
    }
    private val getAvatarLD = MutableLiveData<String>()
    val AvatarLD = getAvatarLD.switchMap{ result ->
        Repository.getAvatar(result)
    }

    fun userLogin(user: User){
        getUserLoginLD.value = user
    }

    fun getAvatar(FileName: String) {
        getAvatarLD.value = FileName
    }

    fun saveUser(user: User) = Repository.saveUser(user)

}