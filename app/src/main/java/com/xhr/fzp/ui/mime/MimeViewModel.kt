package com.xhr.fzp.ui.mime

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class MimeViewModel : ViewModel() {

    private val getAvatarLD = MutableLiveData<String>()
    val avatarLD = Transformations.switchMap(getAvatarLD) { result ->
        Repository.getAvatar(result)
    }

    fun isUserLogin() = Repository.isUserLogin()

    fun getSavedUser() = Repository.getSavedUser()

    //网络获取头像
    fun getNetAvatar(fileName: String) {
        getAvatarLD.value = fileName
    }

    //查看本地是否有头像
    fun getLocalAvatar(): Bitmap {
        return Repository.getUserAvatarFormLocal()
    }

    fun isLocalAvatar(): Boolean {
        return Repository.isLocalAvatar()
    }

    fun saveUserAvatarToLocal(avatar: Bitmap) {
        Repository.saveUserAvatarToLocal(avatar)
    }


}