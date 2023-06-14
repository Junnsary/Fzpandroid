package com.xhr.fzp.logic.model
import com.google.gson.annotations.SerializedName
import java.util.*
open class Source(
    val id: Int,
    val title: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
    val manager: Manager,
    val status: String,
    val tag: Tag,
    val cover: String
)
