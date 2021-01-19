package dev.kuronosu.deguvon.datasource.localstorage.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.EpisodeRoomModel
import dev.kuronosu.deguvon.model.Episode

class EpisodeRoomModelMapper : Mapper<List<EpisodeRoomModel>, List<Episode>> {
    override fun map(data: List<EpisodeRoomModel>): List<Episode> {
        val list = ArrayList<Episode>()
        data.forEach {
            list.add(
                Episode(
                    number = it.number.toFloat(),
                    eid = it.eid,
                    url = it.eid,
                    img = it.img
                )
            )
        }
        return list
    }
}

class EpisodeToEpisodeRoomModelMapper : Mapper<List<Episode>, List<EpisodeRoomModel>> {
    override fun map(data: List<Episode>): List<EpisodeRoomModel> {
        val list = ArrayList<EpisodeRoomModel>()
        data.forEach {
            list.add(
                EpisodeRoomModel(
                    number = it.number.toString(),
                    eid = it.eid,
                    url = it.eid,
                    img = it.img
                )
            )
        }
        return list
    }
}