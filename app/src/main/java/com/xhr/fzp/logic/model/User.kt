package com.xhr.fzp.logic.model

import java.io.Serializable

data class User(
    val id : String,
    val email: String,
    val name : String,
    val passwd: String,
    val avatar : String,
) : Serializable