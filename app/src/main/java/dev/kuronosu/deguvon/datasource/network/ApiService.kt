package dev.kuronosu.deguvon.datasource.network

import dev.kuronosu.deguvon.datasource.network.model.DirectoryNetworkModel
import dev.kuronosu.deguvon.datasource.network.model.LatestEpisodeNetworkModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import java.util.*

interface ApiService {
    @GET("/api/latest")
    suspend fun getLatestEpisodes(): Response<List<LatestEpisodeNetworkModel>>

    @Streaming
    @GET("/api/directory")
    suspend fun getDirectory(): Response<DirectoryNetworkModel>
}