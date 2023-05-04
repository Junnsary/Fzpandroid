package com.xhr.fzp.ui.personal

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.User
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.jar.Attributes.Name

class PersonalViewModel : ViewModel() {

    //获取用户信息
    fun getSavedUser() = Repository.getSavedUser()

    //获取头像
    fun getLocalAvatar(): Bitmap {
        return Repository.getUserAvatarFormLocal()
    }

    fun setUserAvatar(avatar: Bitmap) {
        Repository.saveUserAvatarToLocal(avatar)
    }

    private val getEditUserLD = MutableLiveData<EditUserInfo>()
    val editUserLD = getEditUserLD.switchMap { result ->
        if (result.avatar == null) {
            Repository.editUserInfo(result.userName, result.userEmail, result.userId)
        } else {
            Repository.editUser(
                result.avatar, RequestBody.create(MediaType.parse("text/plain"), result.userName),
                RequestBody.create(MediaType.parse("text/plain"), result.userEmail),
                RequestBody.create(MediaType.parse("text/plain"), result.userId)
            )
        }
    }


    fun editUser(editUserInfo: EditUserInfo) {
        getEditUserLD.value = editUserInfo
    }

    class EditUserInfo(
        val avatar: MultipartBody.Part?,
        val userName: String,
        val userEmail: String,
        val userId: String
    )


    private val getUserLoginLD = MutableLiveData<User>()
    val userLoginLD = getUserLoginLD.switchMap { result ->
        Repository.userLogin(result)
    }

    fun userLogin(user: User) {
        getUserLoginLD.value = user
    }

    fun saveUser(user: User) = Repository.saveUser(user)
}
