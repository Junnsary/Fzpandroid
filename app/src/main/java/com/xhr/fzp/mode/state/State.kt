package com.xhr.fzp.mode.state

import android.content.Context

interface State {
    fun collect(context: Context?, block: () -> Unit)
    fun share(context: Context?, block: () -> Unit)
    fun login(context: Context?, block: () -> Unit)
}