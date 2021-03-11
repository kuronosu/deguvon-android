package dev.kuronosu.deguvon.datasource.localstorage.model

import dev.kuronosu.deguvon.datasource.model.Episode

data class EpisodeRoomModel(
    val number: String,
    val eid: String,
    val url: String,
    val img: String,
)

fun EpisodeRoomModel.asInterfaceModel() = Episode(
    number = number.toFloat(),
    eid = eid,
    url = eid,
    img = img
)

fun List<EpisodeRoomModel>.asInterfaceModel() = map { it.asInterfaceModel() }
