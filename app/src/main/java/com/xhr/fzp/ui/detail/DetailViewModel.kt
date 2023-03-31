package com.xhr.fzp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository

class DetailViewModel : ViewModel() {
    fun isUserLogin() = Repository.isUserLogin()

    private val getCollectionInfoLD = MutableLiveData<CollectionData>()
    val isUserCollectLD = Transformations.switchMap(getCollectionInfoLD){ result ->
        Repository.isUserCollect(result)
    }

    private val addUserCollectionLD = MutableLiveData<CollectionData>()
    val getAddUserCollectionLD = Transformations.switchMap(addUserCollectionLD) { result ->
        Repository.addUserCollection(result)
    }

    private val addUserCommentLD = MutableLiveData<CommentData>()
    val getAddUserCommentLD = Transformations.switchMap(addUserCommentLD) { result ->
        Repository.addUserComment(result)
    }

    private val cancelUserCollectionLD = MutableLiveData<CollectionData>()
    val getCancelUserCollectionLD = Transformations.switchMap(cancelUserCollectionLD) { result ->
        Repository.cancelUserCollection(result)
    }

    fun getAddUserComment(sourceId: Int, tagId: Int, content: String) {
        val user = Repository.getSavedUser()
        addUserCommentLD.value = CommentData(sourceId, tagId, user.id, content)
    }

    fun isUserCollect(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        getCollectionInfoLD.value = CollectionData(sourceId, tagId, user.id)
    }

    fun addUserCollection(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        addUserCollectionLD.value = CollectionData(sourceId, tagId, user.id)
    }

    fun cancelUserCollection(sourceId: Int, tagId: Int) {
        val user = Repository.getSavedUser()
        cancelUserCollectionLD.value = CollectionData(sourceId, tagId, user.id)
    }

    inner class CollectionData(val sourceId: Int, val tagId: Int, val userId:String)
    inner class CommentData(val sourceId: Int, val tagId: Int, val userId: String, val content: String)

}
