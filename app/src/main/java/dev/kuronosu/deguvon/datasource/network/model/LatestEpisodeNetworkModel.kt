package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName

data class LatestEpisodeAnimeDataNetworkModel(
    @SerializedName("id") val id: Number,
    @SerializedName("name") val name: String
)

data class LatestEpisodeNetworkModel(
    @SerializedName("url") val url: String,
    @SerializedName("image") val image: String,
    @SerializedName("capi") val number: String,
    @SerializedName("anime") val anime: LatestEpisodeAnimeDataNetworkModel
)