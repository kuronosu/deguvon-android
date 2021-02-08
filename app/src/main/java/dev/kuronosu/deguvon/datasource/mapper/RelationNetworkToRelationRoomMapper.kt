package dev.kuronosu.deguvon.datasource.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRelationRoomModel
import dev.kuronosu.deguvon.datasource.network.model.RelationNetworkModel

class RelationNetworkToRelationRoomMapper : Mapper<RelationNetworkModel, AnimeRelationRoomModel> {
    override fun map(data: RelationNetworkModel) =
        AnimeRelationRoomModel(data.name, data.url, data.relation)
}

class RelationNetworkListToRelationRoomListMapper :
    Mapper<List<RelationNetworkModel>, List<AnimeRelationRoomModel>> {
    override fun map(data: List<RelationNetworkModel>): List<AnimeRelationRoomModel> {
        val mapper = RelationNetworkToRelationRoomMapper()
        val list = ArrayList<AnimeRelationRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}