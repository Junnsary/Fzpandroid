package com.xhr.fzp.utils.state

import android.content.Context

interface State {
    fun collect(context: Context?, block: () -> Unit)
    fun comment(context: Context?, block: () -> Unit)
    fun login(context: Context?, block: () -> Unit)
}