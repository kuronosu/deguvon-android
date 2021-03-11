package dev.kuronosu.deguvon.datasource.network.model

import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRoomModel
import dev.kuronosu.deguvon.datasource.model.Anime

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

// Network to room converters
fun AnimeNetworkModel.asRoomModel() = AnimeRoomModel(
    flvid = flvid.toString(),
    name = name,
    slug = slug,
    nextEpisodeDate = nextEpisodeDate,
    url = url,
    state = state.toString(),
    type = type.toString(),
    genres = genres.map { it.toString() },
    otherNames = otherNames,
    synopsis = synopsis,
    score = score.toString(),
    votes = votes.toString(),
    cover = cover,
    banner = banner,
    episodes = episodes.asRoomModel(),
    relations = relations.asRoomModel()
)

fun List<AnimeNetworkModel>.asRoomModel() = map { it.asRoomModel() }


// Network to interface converters
fun AnimeNetworkModel.asInterfaceModel(
    states: List<GenericNetworkModel>,
    types: List<GenericNetworkModel>,
    _genres: List<GenericNetworkModel>
) = Anime(
    flvid = flvid,
    name = name,
    slug = slug,
    nextEpisodeDate = nextEpisodeDate,
    url = url,
    state = states.find { it.id == state }!!.asInterfaceModel(),
    type = types.find { it.id == state }!!.asInterfaceModel(),
    genres = _genres.filter { it.id in genres }.asInterfaceModel(),
    otherNames = otherNames,
    synopsis = synopsis,
    score = score,
    votes = votes,
    cover = cover,
    banner = banner,
    episodes = episodes.asInterfaceModel(),
    relations = relations.asInterfaceModel()

)

fun List<AnimeNetworkModel>.asInterfaceModel(
    states: List<GenericNetworkModel>,
    types: List<GenericNetworkModel>,
    genres: List<GenericNetworkModel>
) = map { it.asInterfaceModel(states, types, genres) }
