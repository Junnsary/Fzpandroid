package com.xhr.fzp.other

import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    var counter = 0
        get() = field

    fun add(){
        counter += 1
    }
}