package com.boss.mytodo.network

import com.boss.mytodo.network.model.Motivation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
 In case api changes:

 "https://api.forismatic.com"
 "https://api.quotable.io/"

 @GET("api/1.0/?method=getQuote&lang=en&format=json&json=?")
 @GET("random?tags=inspirational")
*/

interface QuoteApi {

    @GET("api/1.0/?method=getQuote&format=json&json=?")
    suspend fun getMotivation(
        @Query("lang") language: String
    ): Response<Motivation>

}