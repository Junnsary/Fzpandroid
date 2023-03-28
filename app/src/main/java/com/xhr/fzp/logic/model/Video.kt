package com.xhr.fzp.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Video(
    @SerializedName("file_name") val fileName: String,
    id: Int,
    title: String,
    createdAt: Date,
    updatedAt: Date,
    manager: Manager,
    status: String,
    tag: Tag,
    cover: String
) : Source(
    id,
    title,
    createdAt,
    updatedAt,
    manager,
    status,
    tag,
    cover
)