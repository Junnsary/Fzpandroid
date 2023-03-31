package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Favorites(
    val id : Int,
    @SerializedName("created_at") val createdAt : Date,
    val status : String,
    val source: Source,
    val tag : Tag,
    val user : User
)