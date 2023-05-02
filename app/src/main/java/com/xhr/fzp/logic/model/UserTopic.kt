package com.xhr.fzp.logic.model

import java.util.*

data class UserTopic(
    val id: Int,
    val user_id: String,
    val created_at: Date,
    val score: Int,
    val suggestion: String,
    val topic_right: Int,
    val topic_error: Int,
    val topic_sum: Int
)
