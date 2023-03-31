package com.xhr.fzp.mode.state

import android.content.Context

class LoginState : State {
    override fun collect(context: Context?, block: () -> Unit) {
        block()
    }

    override fun comment(context: Context?, block: () -> Unit) {
        block()
    }

    override fun login(context: Context?, block: () -> Unit) {
        //登录之后要执行的操作
        block()
    }
}