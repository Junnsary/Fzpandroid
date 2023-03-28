package com.xhr.fzp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.User

class LoginViewModel : ViewModel() {

    private val getUserLoginLD = MutableLiveData<User>()
    val userLoginLD = Transformations.switchMap(getUserLoginLD){ result ->
        Repository.userLogin(result)
    }
    private val getAvatarLD = MutableLiveData<String>()
    val AvatarLD = Transformations.switchMap(getAvatarLD){ result ->
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