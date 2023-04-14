package com.xhr.fzp.logic.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xhr.fzp.logic.room.dao.SaveDao
import com.xhr.fzp.logic.room.entity.Save

@Database(version = 1, entities = [Save::class],exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun saveDao(): SaveDao

    companion object {
        private var instance : AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "fzp")
                .allowMainThreadQueries()
                .build()
                .apply {
                    instance = this
                }
        }

    }
}