package dev.kuronosu.deguvon.datasource.localstorage.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.localstorage.model.GenericRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.GenreRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.StateRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.TypeRoomModel
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

class GenericToStateRoomModelMapper : Mapper<Generic, StateRoomModel> {
    override fun map(data: Generic) = StateRoomModel(id = data.id.toString(), name = data.name)
}

class GenericToTypeRoomModelMapper : Mapper<Generic, TypeRoomModel> {
    override fun map(data: Generic) = TypeRoomModel(id = data.id.toString(), name = data.name)
}

class GenericToGenreRoomModelMapper : Mapper<Generic, GenreRoomModel> {
    override fun map(data: Generic) = GenreRoomModel(id = data.id.toString(), name = data.name)
}

class GenericListToStateRoomModelListMapper :
    Mapper<List<Generic>, List<StateRoomModel>> {
    override fun map(data: List<Generic>): List<StateRoomModel> {
        val mapper = GenericToStateRoomModelMapper()
        val list = ArrayList<StateRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class GenericListToTypeRoomModelListMapper :
    Mapper<List<Generic>, List<TypeRoomModel>> {
    override fun map(data: List<Generic>): List<TypeRoomModel> {
        val mapper = GenericToTypeRoomModelMapper()
        val list = ArrayList<TypeRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class GenericListToGenreRoomModelListMapper :
    Mapper<List<Generic>, List<GenreRoomModel>> {
    override fun map(data: List<Generic>): List<GenreRoomModel> {
        val mapper = GenericToGenreRoomModelMapper()
        val list = ArrayList<GenreRoomModel>()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}
