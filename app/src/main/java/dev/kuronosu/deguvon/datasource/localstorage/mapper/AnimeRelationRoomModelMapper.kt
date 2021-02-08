package dev.kuronosu.deguvon.datasource.localstorage.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRelationRoomModel
import dev.kuronosu.deguvon.model.Relation


class AnimeRelationRoomModelMapper : Mapper<List<AnimeRelationRoomModel>, List<Relation>> {
    override fun map(data: List<AnimeRelationRoomModel>): List<Relation> {
        val list = ArrayList<Relation>()
        data.forEach { list.add(Relation(name = it.name, url = it.url, relation = it.rel)) }
        return list
    }
}

class RelationToAnimeRelationRoomModelMapper :
    Mapper<List<Relation>, List<AnimeRelationRoomModel>> {
    override fun map(data: List<Relation>): List<AnimeRelationRoomModel> {
        val list = ArrayList<AnimeRelationRoomModel>()
        data.forEach {
            list.add(
                AnimeRelationRoomModel(name = it.name, url = it.url, rel = it.relation)
            )
        }
        return list
    }
}