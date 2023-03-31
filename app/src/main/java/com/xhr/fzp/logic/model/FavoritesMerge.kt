package com.xhr.fzp.logic.model

data class FavoritesMerge(
    val all: List<Favorites>,
    val articles: List<Favorites>,
    val videos: List<Favorites>
)