package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRelationRoomModel
import dev.kuronosu.deguvon.datasource.model.Relation

data class RelationNetworkModel(
    val name: String,
    val url: String,
    @SerializedName("rel") val relation: String
)

// Network to room converters
fun RelationNetworkModel.asRoomModel() = AnimeRelationRoomModel(name, url, relation)
fun List<RelationNetworkModel>.asRoomModel() = map { it.asRoomModel() }

// Network to interface converters
fun RelationNetworkModel.asInterfaceModel() = Relation(name = name, url = url, relation = relation)
fun List<RelationNetworkModel>.asInterfaceModel() = map { it.asInterfaceModel() }
