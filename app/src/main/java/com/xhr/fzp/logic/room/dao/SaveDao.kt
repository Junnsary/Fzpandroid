package com.xhr.fzp.logic.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xhr.fzp.logic.room.entity.Save

@Dao
interface SaveDao {

    @Insert
    suspend fun insertSaveList(saves: List<Save>)

    @Query("select * from Save where category = :category and name = :name and type in (:type)")
    fun querySaveByPC(category: String, name: String, type: Array<String>) : LiveData<List<Save>>

    @Query("select * from Save")
    fun queryAll() : LiveData<List<Save>>

    @Query("delete from Save where category = :category and name = :name")
    suspend fun deleteSaveByPC(category: String, name: String)
}