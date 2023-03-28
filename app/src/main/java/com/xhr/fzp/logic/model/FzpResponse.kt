package com.xhr.fzp.logic.model

class FzpResponse<T>(
    val success : Boolean,
    val code : Int,
    val message : String,
    val data : T
)