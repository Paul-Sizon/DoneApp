package com.example.mytodo.network

import com.example.mytodo.network.model.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.quotable.io/"
    //"https://jsonplaceholder.typicode.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


interface SimpleApi {
   // @GET("posts/1")
   @GET("random?tags=inspirational")
    suspend fun getPost(): Response<Post>
}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object retroApi {
    val api : SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java) }
}