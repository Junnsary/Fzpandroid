package com.xhr.fzp.utils.state

import android.content.Context
import com.xhr.fzp.logic.dao.UserDao

object UserContext {
    private var isLogin : Boolean = UserDao.isUserLogin()

    var mState: State = if (isLogin) LoginState() else LogoutState()

    fun collect(context: Context?, block: () -> Unit) {
        mState.collect(context, block)
    }

    fun comment(context: Context?, block: () -> Unit) {
        mState.comment(context, block)
    }

    fun login(context: Context?, block: () -> Unit) {
        mState.login(context, block)
    }

    fun setLoginState() {
        isLogin = true
        mState = LoginState()
    }

    fun setLogoutState() {
        isLogin = false
        mState = LogoutState()
    }

}