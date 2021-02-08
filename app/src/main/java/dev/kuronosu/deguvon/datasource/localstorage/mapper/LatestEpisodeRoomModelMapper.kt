package dev.kuronosu.deguvon.datasource.localstorage.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeAnimeDataRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel
import dev.kuronosu.deguvon.model.Generic
import dev.kuronosu.deguvon.model.LatestEpisode

class LatestEpisodeRoomModelMapper : Mapper<LatestEpisodeRoomModel, LatestEpisode> {
    override fun map(data: LatestEpisodeRoomModel) = LatestEpisode(
        url = data.url,
        image = data.image,
        capi = data.number,
        anime = Generic(data.anime.id.toInt(), data.anime.name)
    )
}

class LatestEpisodeRoomModelListMapper : Mapper<List<LatestEpisodeRoomModel>, List<LatestEpisode>> {
    override fun map(data: List<LatestEpisodeRoomModel>): List<LatestEpisode> {
        val latestEpisodeRoomModelMapper = LatestEpisodeRoomModelMapper()
        val list = ArrayList<LatestEpisode>()
        data.forEach { list.add(latestEpisodeRoomModelMapper.map(it)) }
        return list
    }
}

class LatestEpisodeToLatestEpisodeRoomModelMapper : Mapper<LatestEpisode, LatestEpisodeRoomModel> {
    override fun map(data: LatestEpisode) = LatestEpisodeRoomModel(
        url = data.url, image = data.image, number = data.capi,
        anime = LatestEpisodeAnimeDataRoomModel(data.anime.id.toString(), data.anime.name)
    )
}

class LatestEpisodeToLatestEpisodeRoomModelListMapper :
    Mapper<List<LatestEpisode>, List<LatestEpisodeRoomModel>> {
    override fun map(data: List<LatestEpisode>): List<LatestEpisodeRoomModel> {
        val latestEpisodeToLatestEpisodeRoomModelMapper =
            LatestEpisodeToLatestEpisodeRoomModelMapper()
        val list = ArrayList<LatestEpisodeRoomModel>()
        data.forEach { list.add(latestEpisodeToLatestEpisodeRoomModelMapper.map(it)) }
        return list
    }
}