package dev.kuronosu.deguvon.network

import dev.kuronosu.deguvon.model.LatestEpisode
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET("/api/latest")
    suspend fun getLatest(): Response<List<LatestEpisode>>
}