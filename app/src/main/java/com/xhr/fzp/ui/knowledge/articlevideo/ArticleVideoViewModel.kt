package com.xhr.fzp.ui.knowledge.articlevideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Tag

class ArticleVideoViewModel : ViewModel(){
    val articleTagList = ArrayList<Tag>()
    val videoTagList = ArrayList<Tag>()

    private val articleVideoTagLD = MutableLiveData<Any?>()
    val getArticleVideoTagLD = articleVideoTagLD.switchMap { _ ->
        Repository.getKnowledge()
    }

    fun getArticleVideoTag(){
        if (articleTagList.size == 0 && videoTagList.size == 0) {
            articleVideoTagLD.value = articleVideoTagLD.value
        }
    }

}