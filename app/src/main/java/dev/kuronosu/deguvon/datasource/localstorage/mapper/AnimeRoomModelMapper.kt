package dev.kuronosu.deguvon.datasource.localstorage.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.*
import dev.kuronosu.deguvon.model.Anime

class AnimeRoomModelMapper(
    private val states: List<StateRoomModel>,
    private val types: List<TypeRoomModel>,
    private val genres: List<GenreRoomModel>
) : Mapper<AnimeRoomModel, Anime> {
    override fun map(data: AnimeRoomModel) = Anime(
        flvid = data.flvid.toInt(),
        name = data.name,
        slug = data.slug,
        nextEpisodeDate = data.nextEpisodeDate,
        url = data.url,
        state = GenericRoomModelMapper().map(states.find { it.id == data.state } as GenericRoomModel),
        type = GenericRoomModelMapper().map(types.find { it.id == data.state } as GenericRoomModel),
        genres = GenericListRoomModelMapper().map(genres.filter { it.id in data.genres }),
        otherNames = data.otherNames,
        synopsis = data.synopsis,
        score = data.score.toFloat(),
        votes = data.votes.toInt(),
        cover = data.cover,
        banner = data.banner,
        episodes = EpisodeRoomModelMapper().map(data.episodes),
        relations = AnimeRelationRoomModelMapper().map(data.relations)
    )
}

class AnimeRoomModelListMapper(
    private val states: List<StateRoomModel>,
    private val types: List<TypeRoomModel>,
    private val genres: List<GenreRoomModel>
) : Mapper<List<AnimeRoomModel>, List<Anime>> {
    override fun map(data: List<AnimeRoomModel>): List<Anime> {
        val animeRoomMapper = AnimeRoomModelMapper(states, types, genres)
        val list = ArrayList<Anime>()
        data.forEach { list.add(animeRoomMapper.map(it)) }
        return list
    }
}