package com.xhr.fzp.ui.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Comment

class CommentViewModel : ViewModel() {

    val commentList = ArrayList<Comment>()

    private val getCommentListLD = MutableLiveData<Pair<Int, Int>>()
    val commentListLd = Transformations.switchMap(getCommentListLD){ result ->
        Repository.getCommentList(result.first, result.second)
    }

    fun getCommentList(sourceId: Int, tagId: Int) {
        getCommentListLD.value = Pair(sourceId, tagId)
    }
}
