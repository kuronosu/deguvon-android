package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName
import dev.kuronosu.deguvon.datasource.localstorage.model.GenericRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.GenreRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.StateRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.TypeRoomModel
import dev.kuronosu.deguvon.datasource.model.Generic

data class GenericNetworkModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)


// Network to room converters
fun GenericNetworkModel.asRoomModel() = GenericRoomModel(id.toString(), name)
fun GenericNetworkModel.asGenreRoomModel() = GenreRoomModel(id.toString(), name)
fun GenericNetworkModel.asTypeRoomModel() = TypeRoomModel(id.toString(), name)
fun GenericNetworkModel.asStateRoomModel() = StateRoomModel(id.toString(), name)

fun List<GenericNetworkModel>.asRoomModel() = map { it.asRoomModel() }
fun List<GenericNetworkModel>.asGenreRoomModel() = map { it.asGenreRoomModel() }
fun List<GenericNetworkModel>.asTypeRoomModel() = map { it.asTypeRoomModel() }
fun List<GenericNetworkModel>.asStateRoomModel() = map { it.asStateRoomModel() }

// This doesn't work but it would be beautiful if it did
//fun <T: GenericRoomModel> GenericNetworkModel.asRoomModel() = GenericRoomModel(id.toString(), name) as T
//fun <T: GenericRoomModel> List<GenericNetworkModel>.asRoomModel() = map { it.asRoomModel<T>() }

// Network to interface converters
fun GenericNetworkModel.asInterfaceModel() = Generic(id, name)
fun List<GenericNetworkModel>.asInterfaceModel() = map { it.asInterfaceModel() }
