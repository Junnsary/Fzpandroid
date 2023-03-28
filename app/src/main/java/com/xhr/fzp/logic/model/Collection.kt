package com.xhr.fzp.logic.model

import java.time.LocalDateTime

class Collection(
    val id : Int,
    val sourceId : Int,
    val type : String,
    val createdAt : LocalDateTime,
    val status : String,
    val user : User
)