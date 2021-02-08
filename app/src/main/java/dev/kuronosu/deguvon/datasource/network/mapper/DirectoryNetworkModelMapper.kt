package dev.kuronosu.deguvon.datasource.network.mapper

import dev.kuronosu.deguvon.datasource.Mapper
import dev.kuronosu.deguvon.datasource.network.model.DirectoryNetworkModel
import dev.kuronosu.deguvon.model.Anime

class DirectoryNetworkModelMapper : Mapper<DirectoryNetworkModel, List<Anime>> {
    override fun map(data: DirectoryNetworkModel): List<Anime> {
        val mapper = AnimeNetworkModelMapper(data.states, data.types, data.genres)
        return data.animes.map { mapper.map(it) }
    }
}