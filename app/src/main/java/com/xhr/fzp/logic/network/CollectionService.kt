package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import retrofit2.Call
import retrofit2.http.*

interface CollectionService {

    @GET("api/collection/iscollect")
    fun isUserCollect(@Query("sourceid") sourceId: Int, @Query("tagid") tagId: Int, @Query("userid") userId: String) : Call<FzpResponse<Boolean>>

    @FormUrlEncoded
    @POST("api/collection")
    fun addUserCollection(@Field("sourceid") sourceId: Int, @Field("tagid") tagId: Int, @Field("userid") userId: String): Call<FzpResponse<Message>>

//    @HTTP(method = "DELETE", path = "api/collection", hasBody = true)
    @FormUrlEncoded
    @POST("api/collection/cancel")
    fun cancelUserCollection(@Field("sourceid") sourceId: Int, @Field("tagid") tagId: Int, @Field("userid") userId: String): Call<FzpResponse<Message>>
}