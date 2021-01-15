package dev.kuronosu.deguvon.datasource.localstorage.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestAndAnime
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeAnimeDataRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel
import dev.kuronosu.deguvon.model.LatestEpisode
import dev.kuronosu.deguvon.model.LatestEpisodeAnimeData

class LatestEpisodeRoomModelMapper : Mapper<LatestAndAnime, LatestEpisode> {
    override fun map(data: LatestAndAnime) = LatestEpisode(
        url = data.latest.url,
        image = data.latest.image,
        capi = data.latest.number,
        anime = LatestEpisodeAnimeData(data.anime.aid.toInt(), data.anime.name)
    )
}

class LatestEpisodeRoomModelListMapper : Mapper<List<LatestAndAnime>, List<LatestEpisode>> {
    override fun map(data: List<LatestAndAnime>): List<LatestEpisode> {
        val latestEpisodeRoomModelMapper = LatestEpisodeRoomModelMapper()
        val list = ArrayList<LatestEpisode>()
        data.forEach { list.add(latestEpisodeRoomModelMapper.map(it)) }
        return list
    }
}

class LatestEpisodeToLatestEpisodeRoomModelMapper : Mapper<LatestEpisode, LatestAndAnime> {
    override fun map(data: LatestEpisode) = LatestAndAnime(
        latest = LatestEpisodeRoomModel(data.url, data.image, data.capi),
        anime = LatestEpisodeAnimeDataRoomModel(data.url, data.anime.id.toString(), data.anime.name)
    )
}

class LatestEpisodeToLatestEpisodeRoomModelListMapper :
    Mapper<List<LatestEpisode>, List<LatestAndAnime>> {
    override fun map(data: List<LatestEpisode>): List<LatestAndAnime> {
        val latestEpisodeToLatestEpisodeRoomModelMapper =
            LatestEpisodeToLatestEpisodeRoomModelMapper()
        val list = ArrayList<LatestAndAnime>()
        data.forEach { list.add(latestEpisodeToLatestEpisodeRoomModelMapper.map(it)) }
        return list
    }

}