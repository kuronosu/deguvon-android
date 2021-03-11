package dev.kuronosu.deguvon.datasource.localstorage.model

import dev.kuronosu.deguvon.datasource.model.Relation

data class AnimeRelationRoomModel(
    val name: String,
    val url: String,
    val rel: String,
)

fun AnimeRelationRoomModel.asInterfaceModel() = Relation(name = name, url = url, relation = rel)

fun List<AnimeRelationRoomModel>.asInterfaceModel() = map { it.asInterfaceModel() }
