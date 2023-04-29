package com.xhr.fzp.ui.personal

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class PersonalViewModel : ViewModel() {

    //获取用户信息
    fun getSavedUser() = Repository.getSavedUser()

    //获取头像
    fun getLocalAvatar(): Bitmap {
        return Repository.getUserAvatarFormLocal()
    }

}
