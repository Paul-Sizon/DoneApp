package com.example.mytodo.network

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {
    private val httpClient: OkHttpClient

    private val cache: Cache

    private val cacheSize = 20 * 1024 * 1024 // 20MB

    init {
        cache = Cache(context.cacheDir, cacheSize.toLong())

        httpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    }

//    fun enqueue(request: Request, callback: Callback?) {
//        httpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.i(TAG, "onFailure " + e.message)
//                callback?.onFailure(call, e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.i(TAG, "onResponse $response")
//                if (response.isSuccessful || response.code == 304) {
//                    callback?.onResponse(call, response)
//                } else {
//                    var body: String? = null
//                    var json: String? = null
//                    try {
//                        body = response.body?.string()
////                        json = Gson().toJson(body)
//                    } catch (e: Exception) {
//                    }
//                    callback?.onFailure(call, ApiClientException(response.code, body, json))
//                }
//
//            }
//        })
//    }

    class ApiClientException(val code: Int, val response: String? = null, val json: Any?) :
        IOException()

    companion object {
        private val TAG: String = this::class.java.enclosingClass?.simpleName ?: "TAG"
    }
}