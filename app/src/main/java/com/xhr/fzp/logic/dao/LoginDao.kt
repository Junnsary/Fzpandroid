package com.xhr.fzp.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.xhr.fzp.FzpApplication
import com.xhr.fzp.logic.model.User

object LoginDao {
    private const val USER_INFO = "user_info"
    private const val USER = "user"

    fun saveUser(user: User) {
        sharedPreferences().edit {
            putString(USER, Gson().toJson(user))
        }
    }

    fun getSavedUser() : User {
        val userJson = sharedPreferences().getString(USER, "")
        return Gson().fromJson(userJson, User::class.java)
    }

    fun isUserLogin() = sharedPreferences().contains(USER)

    private fun sharedPreferences() = FzpApplication.context
        .getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
}