package dev.kuronosu.deguvon.datasource.network.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.network.model.EpisodeNetworkModel
import dev.kuronosu.deguvon.model.Episode

class EpisodeNetworkModelMapper : Mapper<EpisodeNetworkModel, Episode> {
    override fun map(data: EpisodeNetworkModel) =
        Episode(number = data.number, eid = data.eid, url = data.url, img = data.img)
}

class EpisodeNetworkModelListMapper : Mapper<List<EpisodeNetworkModel>, List<Episode>> {
    override fun map(data: List<EpisodeNetworkModel>): List<Episode> {
        val mapper = EpisodeNetworkModelMapper()
        val list = ArrayList<Episode>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class EpisodeToEpisodeNetworkModelMapper : Mapper<Episode, EpisodeNetworkModel> {
    override fun map(data: Episode) =
        EpisodeNetworkModel(number = data.number, eid = data.eid, url = data.url, img = data.img)
}

class EpisodeListToEpisodeNetworkModelListMapper :
    Mapper<List<Episode>, List<EpisodeNetworkModel>> {
    override fun map(data: List<Episode>): List<EpisodeNetworkModel> {
        val mapper = EpisodeToEpisodeNetworkModelMapper()
        val list = ArrayList<EpisodeNetworkModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}