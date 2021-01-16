package dev.kuronosu.deguvon.datasource.localstorage.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.GenericRoomModel
import dev.kuronosu.deguvon.model.Generic

class GenericRoomModelMapper : Mapper<GenericRoomModel, Generic> {
    override fun map(data: GenericRoomModel) = Generic(data.id.toInt(), data.name)
}

class GenericToGenericRoomModelMapper<T : GenericRoomModel> : Mapper<Generic, GenericRoomModel> {
    override fun map(data: Generic): T =
        GenericRoomModel(id = data.id.toString(), name = data.name) as T
}
