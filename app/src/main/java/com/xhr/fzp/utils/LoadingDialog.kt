package com.xhr.fzp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.xhr.fzp.R

class LoadingDialog(context: Context, private val message: String) : Dialog(context, R.style.NormalDialogStyle) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_loading)
        val tv = findViewById<TextView>(R.id.tv_loading_message)
        tv.text = message
    }
}