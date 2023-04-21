package com.xhr.fzp.logic.model

import android.text.InputFilter
import android.text.Spanned

class SpaceFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
//        LogUtil.d(this, "source: ${source}, start: ${start}, end: ${end}, dest: ${dest}, dstart: ${dstart}, dend: ${dend}")
        source?.let {
            if (it == " ") {
                return ""
            }
        }
        return null
    }
}


class InputNumFilter(private val num: Int): InputFilter{
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        return if (dstart >= num) {
            ""
        } else {
            null
        }
    }

}