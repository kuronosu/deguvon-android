package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.PrimaryKey
import dev.kuronosu.deguvon.datasource.model.Generic

open class GenericRoomModel(
    @PrimaryKey val id: String,
    val name: String
)

fun GenericRoomModel.asInterfaceModel() = Generic(id.toInt(),  name)

fun List<GenericRoomModel>.asInterfaceModel() = map { it.asInterfaceModel() }