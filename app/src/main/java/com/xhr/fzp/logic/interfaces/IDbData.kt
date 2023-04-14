package com.xhr.fzp.logic.interfaces

import androidx.lifecycle.LiveData
import com.xhr.fzp.logic.room.entity.Save

interface IDbData {
    fun saveSourceToDatabase(saves: List<Save>)

    fun getSourceFromDatabase(category: String, name: String, type: Array<String>): LiveData<List<Save>>
}