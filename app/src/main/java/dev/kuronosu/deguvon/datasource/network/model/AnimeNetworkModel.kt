package dev.kuronosu.deguvon.datasource.network.model

data class AnimeNetworkModel(
    val flvid: Int,
    val name: String,
    val slug: String,
    val nextEpisodeDate: String,
    val url: String,
    val state: Int,
    val type: Int,
    val genres: List<Int>,
    val otherNames: List<String>,
    val synopsis: String,
    val score: Float,
    val votes: Int,
    val cover: String,
    val banner: String,
    val relations: List<RelationNetworkModel>,
    val episodes: List<EpisodeNetworkModel>
)
