package com.xhr.fzp.logic.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageService {

    @GET("uploads/images/avatars/{avatar_name}")
    fun getAvatar(@Path("avatar_name") fileName: String ) : Call<ResponseBody>

}