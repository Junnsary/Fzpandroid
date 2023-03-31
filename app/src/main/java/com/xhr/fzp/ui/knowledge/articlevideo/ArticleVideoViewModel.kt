package com.xhr.fzp.ui.knowledge.articlevideo

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.Tag

class ArticleVideoViewModel : ViewModel() {
    val articleTagList = ArrayList<Tag>()
    val videoTagList = ArrayList<Tag>()
    val tags = arrayOf("文章学习", "视频学习")
    val fragments = ArrayList<Fragment>()

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