package dev.kuronosu.deguvon.datasource.network

import dev.kuronosu.deguvon.datasource.network.model.LatestEpisodeNetworkModel
import dev.kuronosu.deguvon.model.LatestEpisode
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/latest")
    suspend fun getLatestEpisodes(): Response<List<LatestEpisodeNetworkModel>>
}