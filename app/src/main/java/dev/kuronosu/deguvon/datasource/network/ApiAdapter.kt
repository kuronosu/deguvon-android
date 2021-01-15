package dev.kuronosu.deguvon.datasource.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter {
    val API_SERVICE: ApiService = Retrofit.Builder()
        .baseUrl("https://kuronosu.dev")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}