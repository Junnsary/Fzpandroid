package com.xhr.fzp.utils.state

import android.content.Context
import android.content.Intent
import com.xhr.fzp.ui.login.LoginActivity

class LogoutState : State {

    override fun collect(context: Context?, block: () -> Unit) {
        goLoginActivity(context)
    }

    override fun comment(context: Context?, block: () -> Unit) {
        goLoginActivity(context)
    }

    override fun login(context: Context?, block: () -> Unit) {
        goLoginActivity(context)
    }

    private fun goLoginActivity(context: Context?) {
        context?.run {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}