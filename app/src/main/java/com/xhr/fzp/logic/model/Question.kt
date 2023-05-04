package com.xhr.fzp.logic.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Question(
    val content: String,
    val user: User,
    val id: Int = 0,
    val review: Int = 0,
    @SerializedName("created_at") val createdAt: Date = Date(),
    val status: String = "normal",
    @SerializedName("answer_num") val answerNum: Int = 0,
) : Parcelable