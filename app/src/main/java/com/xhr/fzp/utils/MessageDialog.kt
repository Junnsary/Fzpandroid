package com.xhr.fzp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.xhr.fzp.R

class MessageDialog(context: Context, private val message: String) : Dialog(context, R.style.NormalDialogStyle) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_message)
        val tv = findViewById<TextView>(R.id.tv_message)
        tv.text = message
    }
}