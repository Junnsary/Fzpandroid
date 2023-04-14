package com.xhr.fzp.logic.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Save(
    @ColumnInfo("source_id") var sourceId: Int,
    @ColumnInfo("tag_id") var tagId: Int,
    var type: String,
    var title: String,
    var cover: String,
    @ColumnInfo("manager_name") var managerName: String,
    var date: Long,
    var category: String,
    var name: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}