package com.xhr.fzp.ui.setting

import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class SettingViewModel : ViewModel() {

    fun clearUser()  = Repository.clearUser()

    fun clearLocalUserAvatar() = Repository.clearLocalUserAvatar()

}
