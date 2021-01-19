package dev.kuronosu.deguvon.datasource.network.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.network.model.RelationNetworkModel
import dev.kuronosu.deguvon.model.Relation

class RelationNetworkModelMapper : Mapper<RelationNetworkModel, Relation> {
    override fun map(data: RelationNetworkModel) =
        Relation(name = data.name, url = data.url, relation = data.relation)
}

class RelationNetworkModelListMapper : Mapper<List<RelationNetworkModel>, List<Relation>> {
    override fun map(data: List<RelationNetworkModel>): List<Relation> {
        val list = ArrayList<Relation>()
        val mapper = RelationNetworkModelMapper()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class RelationToRelationNetworkModelMapper : Mapper<Relation, RelationNetworkModel> {
    override fun map(data: Relation) =
        RelationNetworkModel(name = data.name, url = data.url, relation = data.relation)
}

class RelationListToRelationNetworkModelListMapper :
    Mapper<List<Relation>, List<RelationNetworkModel>> {
    override fun map(data: List<Relation>): List<RelationNetworkModel> {
        val list = ArrayList<RelationNetworkModel>()
        val mapper = RelationToRelationNetworkModelMapper()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}