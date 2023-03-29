package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Collection(
    val id : Int,
    val source: Source,
    val tag : Tag,
    @SerializedName("created_at") val createdAt : Date,
    val status : String,
    val user : User
)