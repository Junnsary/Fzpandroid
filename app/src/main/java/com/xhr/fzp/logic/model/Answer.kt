package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Answer(
    val id : Int,
    val content: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("question_id") val questionId: Int,
    val user: User
){
}