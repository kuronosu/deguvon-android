package dev.kuronosu.deguvon.model

data class LatestEpisodeAnimeData(val id: Number, val name: String)

data class LatestEpisode(
    val url: String,
    val image: String,
    val capi: String,
    val anime: LatestEpisodeAnimeData
)