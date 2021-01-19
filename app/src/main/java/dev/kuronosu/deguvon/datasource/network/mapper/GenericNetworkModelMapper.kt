package dev.kuronosu.deguvon.datasource.network.mapper

import coil.map.Mapper
import dev.kuronosu.deguvon.datasource.network.model.GenericNetworkModel
import dev.kuronosu.deguvon.model.Generic

class GenericNetworkModelMapper : Mapper<GenericNetworkModel, Generic> {
    override fun map(data: GenericNetworkModel) = Generic(id = data.id, name = data.name)
}

class GenericNetworkModelListMapper : Mapper<List<GenericNetworkModel>, List<Generic>> {
    override fun map(data: List<GenericNetworkModel>): List<Generic> {
        val list = ArrayList<Generic>()
        val mapper = GenericNetworkModelMapper()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}

class GenericToGenericNetworkModelMapper : Mapper<Generic, GenericNetworkModel> {
    override fun map(data: Generic) = GenericNetworkModel(id = data.id, name = data.name)
}

class GenericListToGenericNetworkModelListMapper :
    Mapper<List<Generic>, List<GenericNetworkModel>> {
    override fun map(data: List<Generic>): List<GenericNetworkModel> {
        val list = ArrayList<GenericNetworkModel>()
        val mapper = GenericToGenericNetworkModelMapper()
        data.forEach { list.add(mapper.map(it)) }
        return list
    }
}