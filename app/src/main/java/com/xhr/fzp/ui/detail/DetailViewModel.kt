package com.xhr.fzp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository

class DetailViewModel : ViewModel() {
    fun isUserLogin() = Repository.isUserLogin()

    private val getFavoritesInfoLD = MutableLiveData<FavoritesData>()
    val isUserCollectLD = getFavoritesInfoLD.switchMap { result ->
        Repository.isUserCollect(result)
    }

    private val addUserFavoritesLD = MutableLiveData<FavoritesData>()
    val getAddUserFavoritesLD = addUserFavoritesLD.switchMap { result ->
        Repository.addUserFavorites(result)
    }

    private val addUserCommentLD = MutableLiveData<CommentData>()
    val getAddUserCommentLD = addUserCommentLD.switchMap { result ->
        Repository.addUserComment(result)
    }

    private val cancelUserFavoritesLD = MutableLiveData<FavoritesData>()
    val getCancelUserFavoritesLD = cancelUserFavoritesLD.switchMap { result ->
        Repository.cancelUserFavorites(result)
    }

    fun getAddUserComment(sourceId: Int, tagId: Int, content: String) {
        val user = Repository.getSavedUser()
        addUserCommentLD.value = CommentData(sourceId, tagId, user.id, content)
    }

    fun isUserCollect(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        getFavoritesInfoLD.value = FavoritesData(sourceId, tagId, user.id)
    }

    fun addUserFavorites(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        addUserFavoritesLD.value = FavoritesData(sourceId, tagId, user.id)
    }

    fun cancelUserFavorites(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        cancelUserFavoritesLD.value = FavoritesData(sourceId, tagId, user.id)
    }

    inner class FavoritesData(val sourceId: Int, val tagId: Int, val userId:String)
    inner class CommentData(val sourceId: Int, val tagId: Int, val userId: String, val content: String)

}
