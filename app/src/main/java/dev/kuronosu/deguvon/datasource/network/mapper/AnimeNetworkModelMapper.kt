package dev.kuronosu.deguvon.datasource.network.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.network.model.AnimeNetworkModel
import dev.kuronosu.deguvon.datasource.network.model.GenericNetworkModel
import dev.kuronosu.deguvon.model.Anime

class AnimeNetworkModelMapper(
    private val states: List<GenericNetworkModel>,
    private val types: List<GenericNetworkModel>,
    private val genres: List<GenericNetworkModel>
) : Mapper<AnimeNetworkModel, Anime> {
    override fun map(data: AnimeNetworkModel) = Anime(
        flvid = data.flvid,
        name = data.name,
        slug = data.slug,
        nextEpisodeDate = data.nextEpisodeDate,
        url = data.url,
        state = GenericNetworkModelMapper().map(states.find { it.id == data.state }!!),
        type = GenericNetworkModelMapper().map(types.find { it.id == data.state }!!),
        genres = GenericNetworkModelListMapper().map(genres.filter { it.id in data.genres }),
        otherNames = data.otherNames,
        synopsis = data.synopsis,
        score = data.score,
        votes = data.votes,
        cover = data.cover,
        banner = data.banner,
        episodes = EpisodeNetworkModelListMapper().map(data.episodes),
        relations = RelationNetworkModelListMapper().map(data.relations)

    )
}

class AnimeToAnimeNetworkModelMapper : Mapper<Anime, AnimeNetworkModel> {
    override fun map(data: Anime) = AnimeNetworkModel(
        flvid = data.flvid,
        name = data.name,
        slug = data.slug,
        nextEpisodeDate = data.nextEpisodeDate,
        url = data.url,
        state = data.state.id,
        type = data.type.id,
        genres = data.genres.map { it.id },
        otherNames = data.otherNames,
        synopsis = data.synopsis,
        score = data.score,
        votes = data.votes,
        cover = data.cover,
        banner = data.banner,
        episodes = EpisodeListToEpisodeNetworkModelListMapper().map(data.episodes),
        relations = RelationListToRelationNetworkModelListMapper().map(data.relations)

    )
}