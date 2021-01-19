package dev.kuronosu.deguvon.datasource.localstorage.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.GenericRoomModel
import dev.kuronosu.deguvon.model.Generic

class GenericRoomModelMapper : Mapper<GenericRoomModel, Generic> {
    override fun map(data: GenericRoomModel) = Generic(data.id.toInt(), data.name)
}

class GenericListRoomModelMapper : Mapper<List<GenericRoomModel>, List<Generic>> {
    override fun map(data: List<GenericRoomModel>): List<Generic> {
        val mapper = GenericRoomModelMapper()
        val list = ArrayList<Generic>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class GenericToGenericRoomModelMapper<T : GenericRoomModel> : Mapper<Generic, T> {
    override fun map(data: Generic): T =
        GenericRoomModel(id = data.id.toString(), name = data.name) as T
}

class GenericListToGenericRoomModelListMapper<T : GenericRoomModel> :
    Mapper<List<Generic>, List<T>> {
    override fun map(data: List<Generic>): List<T> {
        val mapper = GenericToGenericRoomModelMapper<T>()
        val list = ArrayList<T>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }

}
