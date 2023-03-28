package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment(
    val id : Int,
    val content : String,
    @SerializedName("created_at") val createdAt : Date,
    @SerializedName("updated_at") val updatedAt : Date,
    val user : User,
    val sourceId : Int,
    val status : String,
    val tagId : Int
)