package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import com.xhr.fzp.logic.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @FormUrlEncoded
    @POST("api/users/signup")
    fun userSignup(@Field("id") id: String, @Field("email") email: String, @Field("name") name: String, @Field("passwd") passwd: String ) : Call<FzpResponse<Message>>

    @FormUrlEncoded
    @POST("api/users/login")
    fun userLogin(@Field("id") id: String, @Field("passwd") passwd: String) : Call<FzpResponse<User>>
}