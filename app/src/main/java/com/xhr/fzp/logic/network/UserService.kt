package com.xhr.fzp.logic.network

import com.xhr.fzp.logic.model.FzpResponse
import com.xhr.fzp.logic.model.Message
import com.xhr.fzp.logic.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserService {

    @FormUrlEncoded
    @POST("api/users/signup")
    fun userSignup(@Field("id") id: String, @Field("email") email: String, @Field("name") name: String, @Field("passwd") passwd: String ) : Call<FzpResponse<Message>>

    @FormUrlEncoded
    @POST("api/users/login")
    fun userLogin(@Field("id") id: String, @Field("passwd") passwd: String) : Call<FzpResponse<User>>
    @Multipart
    @POST("api/users/edit")
    fun editUser(@Part avatar: MultipartBody.Part, @Part("username") userName: RequestBody, @Part("useremail") userEmail: RequestBody, @Part("id") userId: RequestBody) : Call<FzpResponse<String>>

    @FormUrlEncoded
    @POST("api/users/edit")
    fun editUserInfo(@Field("username") userName: String, @Field("useremail") userEmail: String, @Field("id") userId: String) : Call<FzpResponse<String>>
}