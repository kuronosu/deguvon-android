package dev.kuronosu.deguvon.datasource.mapper


import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRoomModel
import dev.kuronosu.deguvon.datasource.network.model.AnimeNetworkModel

class AnimeNetworkToAnimeRoomMapper : Mapper<AnimeNetworkModel, AnimeRoomModel> {
    override fun map(data: AnimeNetworkModel) = AnimeRoomModel(
        flvid = data.flvid.toString(),
        name = data.name,
        slug = data.slug,
        nextEpisodeDate = data.nextEpisodeDate,
        url = data.url,
        state = data.state.toString(),
        type = data.type.toString(),
        genres = data.genres.map { it.toString() },
        otherNames = data.otherNames,
        synopsis = data.synopsis,
        score = data.score.toString(),
        votes = data.votes.toString(),
        cover = data.cover,
        banner = data.banner,
        episodes = EpisodeNetworkListToEpisodeRoomListMapper().map(data.episodes),
        relations = RelationNetworkListToRelationRoomListMapper().map(data.relations)
    )
}

class AnimeNetworkListToAnimeRoomListMapper :
    Mapper<List<AnimeNetworkModel>, List<AnimeRoomModel>> {
    override fun map(data: List<AnimeNetworkModel>): List<AnimeRoomModel> {
        val mapper = AnimeNetworkToAnimeRoomMapper()
        val list = ArrayList<AnimeRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}