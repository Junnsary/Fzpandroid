package com.xhr.fzp.mode.state

import android.content.Context
import com.xhr.fzp.logic.dao.LoginDao

object UserContext {
    private var isLogin : Boolean = LoginDao.isUserLogin()

    var mState: State = if (isLogin) LoginState() else LogoutState()

    fun collect(context: Context?, block: () -> Unit) {
        mState.collect(context, block)
    }

    fun share(context: Context?, block: () -> Unit) {
        mState.share(context, block)
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