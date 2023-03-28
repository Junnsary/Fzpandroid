package com.xhr.fzp.logic.model

import java.util.*

class Article(
    val content: String,
    cover: String,
    id: Int,
    title: String,
    createdAt: Date,
    updatedAt: Date,
    manager: Manager,
    status: String,
    tag: Tag
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

