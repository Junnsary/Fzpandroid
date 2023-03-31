package com.xhr.fzp.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xhr.fzp.logic.Repository
import com.xhr.fzp.logic.model.FavoritesMerge

class FavoritesViewModel : ViewModel() {

    val mFavoritesMerge = FavoritesMerge(ArrayList(), ArrayList(), ArrayList())

    private val getFavoritesListLD = MutableLiveData<String>()
    val favoritesListLD = getFavoritesListLD.switchMap { result ->
        Repository.getFavoritesList(result)
    }

    fun getFavoritesList() {
        val user = Repository.getSavedUser()
        getFavoritesListLD.value = user.id
    }
}
