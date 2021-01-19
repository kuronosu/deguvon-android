package dev.kuronosu.deguvon.datasource.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.EpisodeRoomModel
import dev.kuronosu.deguvon.datasource.network.model.EpisodeNetworkModel

class EpisodeNetworkToEpisodeRoomMapper : Mapper<EpisodeNetworkModel, EpisodeRoomModel> {
    override fun map(data: EpisodeNetworkModel) =
        EpisodeRoomModel(data.number.toString(), data.eid, data.url, data.img)
}

class EpisodeNetworkListToEpisodeRoomListMapper :
    Mapper<List<EpisodeNetworkModel>, List<EpisodeRoomModel>> {
    override fun map(data: List<EpisodeNetworkModel>): List<EpisodeRoomModel> {
        val mapper = EpisodeNetworkToEpisodeRoomMapper()
        val list = ArrayList<EpisodeRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}