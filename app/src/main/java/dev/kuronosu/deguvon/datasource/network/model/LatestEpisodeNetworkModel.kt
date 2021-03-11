package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeAnimeDataRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel
import dev.kuronosu.deguvon.datasource.model.Generic
import dev.kuronosu.deguvon.datasource.model.LatestEpisode

data class LatestEpisodeNetworkModel(
    @SerializedName("url") val url: String,
    @SerializedName("image") val image: String,
    @SerializedName("capi") val number: String,
    @SerializedName("anime") val anime: GenericNetworkModel
)

// Network to room converters
fun LatestEpisodeNetworkModel.asRoomModel() = LatestEpisodeRoomModel(
    url,
    image,
    number,
    LatestEpisodeAnimeDataRoomModel(anime.id.toString(), anime.name)
)

fun List<LatestEpisodeNetworkModel>.asRoomModel() = map { it.asRoomModel() }

// Network to interface converters
fun LatestEpisodeNetworkModel.asInterfaceModel() = LatestEpisode(
    url = url,
    image = image,
    capi = number,
    anime = Generic(anime.id, anime.name)
)

fun List<LatestEpisodeNetworkModel>.asInterfaceModel() = map { it.asInterfaceModel() }