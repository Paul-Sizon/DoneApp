package com.example.mytodo.network

import android.util.Log
import com.example.mytodo.network.model.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

private const val BASE_URL = "https://api.forismatic.com/"
//    "https://api.forismatic.com"
    //"https://api.quotable.io/"



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


interface SimpleApi {
//   @GET("api/1.0/?method=getQuote&lang=en&format=json&json=?")
// @GET("random?tags=inspirational")

  @GET("api/1.0/?method=getQuote&format=json&json=?")
    suspend fun getPost(
      @Query("lang") language: String
  ): Response<Post>

}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object RetroApi {
    val api : SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java) }
}