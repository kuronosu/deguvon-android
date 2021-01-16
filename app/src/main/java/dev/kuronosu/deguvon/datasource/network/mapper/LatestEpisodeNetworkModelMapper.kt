package dev.kuronosu.deguvon.datasource.network.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.network.model.LatestEpisodeNetworkModel
import dev.kuronosu.deguvon.model.Generic
import dev.kuronosu.deguvon.model.LatestEpisode

class LatestEpisodeNetworkModelMapper : Mapper<LatestEpisodeNetworkModel, LatestEpisode> {
    override fun map(data: LatestEpisodeNetworkModel) = LatestEpisode(
        url = data.url,
        image = data.image,
        capi = data.number,
        anime = Generic(data.anime.id, data.anime.name)
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
