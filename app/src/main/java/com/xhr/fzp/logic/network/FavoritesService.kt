package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FavoritesMerge
import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import retrofit2.Call
import retrofit2.http.*

interface FavoritesService {

    @GET("api/favorites/")
    fun getFavoritesList(@Query("userid") userId: String) : Call<FzpResponse<FavoritesMerge>>

    @GET("api/favorites/isfavorites")
    fun isUserCollect(@Query("sourceid") sourceId: Int, @Query("tagid") tagId: Int, @Query("userid") userId: String) : Call<FzpResponse<Boolean>>

    @FormUrlEncoded
    @POST("api/favorites")
    fun addUserFavorites(@Field("sourceid") sourceId: Int, @Field("tagid") tagId: Int, @Field("userid") userId: String): Call<FzpResponse<Message>>

    @FormUrlEncoded
    @POST("api/favorites/cancel")
    fun cancelUserFavorites(@Field("sourceid") sourceId: Int, @Field("tagid") tagId: Int, @Field("userid") userId: String): Call<FzpResponse<Message>>
}