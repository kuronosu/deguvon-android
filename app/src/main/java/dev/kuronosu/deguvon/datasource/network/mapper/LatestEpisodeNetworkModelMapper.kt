package dev.kuronosu.deguvon.datasource.network.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.network.model.LatestEpisodeAnimeDataNetworkModel
import dev.kuronosu.deguvon.datasource.network.model.LatestEpisodeNetworkModel
import dev.kuronosu.deguvon.model.LatestEpisode
import dev.kuronosu.deguvon.model.LatestEpisodeAnimeData


class LatestEpisodeAnimeDataNetworkModelMapper :
    Mapper<LatestEpisodeAnimeDataNetworkModel, LatestEpisodeAnimeData> {
    override fun map(data: LatestEpisodeAnimeDataNetworkModel) =
        LatestEpisodeAnimeData(id = data.id, name = data.name)
}

class LatestEpisodeNetworkModelMapper : Mapper<LatestEpisodeNetworkModel, LatestEpisode> {
    override fun map(data: LatestEpisodeNetworkModel) = LatestEpisode(
        url = data.url,
        image = data.image,
        capi = data.number,
        anime = LatestEpisodeAnimeDataNetworkModelMapper().map(data.anime)
    )
}

class LatestEpisodeNetworkModelListMapper :
    Mapper<List<LatestEpisodeNetworkModel>, List<LatestEpisode>> {
    override fun map(data: List<LatestEpisodeNetworkModel>): List<LatestEpisode> {
        val latestEpisodeRoomModelListMapper = LatestEpisodeNetworkModelMapper()
        val list = ArrayList<LatestEpisode>()
        data.forEach { list.add(latestEpisodeRoomModelListMapper.map(it)) }
        return list
    }

}
