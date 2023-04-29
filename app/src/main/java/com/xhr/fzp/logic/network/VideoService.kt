package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoService {
    @GET("api/video/{id}")
    fun getVideoInfo(@Path("id") id: Int) : Call<FzpResponse<Video>>
}