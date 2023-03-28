package com.xhr.fzp.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FzpServiceCreator {

    private const val BASE_URL = "http://10.0.2.2:3000/"
    //用户头像的位置
    private const val PUBLIC_IMAGE = "uploads/images/"
    private const val PUBLIC_VIDEO = "uploads/videos/"
    private const val AVATAR_URL = "${PUBLIC_IMAGE}avatars/"
    private const val VIDEO_IMAGE_URL = "${PUBLIC_IMAGE}videos/"
    private const val ARTICLE_IMAGE_URL = "${PUBLIC_IMAGE}articles/"
    private const val GET_ARTICLE = "api/article/"

    fun getUserAvatarUrl(fileName: String) : String {
        return BASE_URL + AVATAR_URL + fileName
    }

    fun getVideoImageURL(fileName: String) : String {
        return BASE_URL + VIDEO_IMAGE_URL + fileName
    }

    fun getArticleURL(id: Int) : String {
        return BASE_URL + GET_ARTICLE + id.toString()
    }

    fun getArticleImageURL(fileName: String) : String {
        return BASE_URL + ARTICLE_IMAGE_URL + fileName
    }


    fun getVideoFilePath(fileName: String): String {
        return BASE_URL + PUBLIC_VIDEO + fileName
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * @param serviceClass 接受一个class类对象
     * @return 返回一个动态代理对象
     */
    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    /**
     * @return 返回一个动态代理对象
     */
    inline fun <reified T> create(): T {
        // create(String::class.java)
        return create(T::class.java)
    }


}
