package com.boss.mytodo.data.repositories


import com.boss.mytodo.network.QuoteApi
import com.boss.mytodo.network.model.Motivation
import retrofit2.Response
import javax.inject.Inject

class MotivRepository@Inject constructor(private val api: QuoteApi) {
    suspend fun getMotivation(language: String): Response<Motivation> {
        return api.getMotivation(language)
    }

}