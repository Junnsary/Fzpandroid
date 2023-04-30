package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Question(
    val content: String,
    val user: User
) : Serializable {
    val id: Int = 0
    val review: Int = 0
    @SerializedName("created_at") val createdAt: Date = Date()
    val status: String = "normal"
    @SerializedName("answer_num")val answerNum: Int = 0
}