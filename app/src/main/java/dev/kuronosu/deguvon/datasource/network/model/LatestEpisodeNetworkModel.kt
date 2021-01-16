package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName

data class LatestEpisodeNetworkModel(
    @SerializedName("url") val url: String,
    @SerializedName("image") val image: String,
    @SerializedName("capi") val number: String,
    @SerializedName("anime") val anime: GenericNetworkModel
)