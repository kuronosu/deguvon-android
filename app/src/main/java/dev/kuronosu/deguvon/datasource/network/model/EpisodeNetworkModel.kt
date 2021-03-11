package dev.kuronosu.deguvon.datasource.network.model

import dev.kuronosu.deguvon.datasource.localstorage.model.EpisodeRoomModel
import dev.kuronosu.deguvon.datasource.model.Episode

data class EpisodeNetworkModel(
    val number: Float,
    val eid: String,
    val url: String,
    val img: String
)

// Network to room converters
fun EpisodeNetworkModel.asRoomModel() = EpisodeRoomModel(number.toString(), eid, url, img)
fun List<EpisodeNetworkModel>.asRoomModel() = map { it.asRoomModel() }

// Network to interface converters
fun EpisodeNetworkModel.asInterfaceModel() = Episode(number, eid, url, img)
fun List<EpisodeNetworkModel>.asInterfaceModel() = map { it.asInterfaceModel() }
