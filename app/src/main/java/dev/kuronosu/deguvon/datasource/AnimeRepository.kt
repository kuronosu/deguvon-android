package dev.kuronosu.deguvon.datasource

import android.content.Context
import dev.kuronosu.deguvon.datasource.localstorage.dao.clearDirectory
import dev.kuronosu.deguvon.datasource.localstorage.dao.insertDirectory
import dev.kuronosu.deguvon.datasource.localstorage.mapper.AnimeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToGenreRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToStateRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToTypeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.mapper.AnimeNetworkListToAnimeRoomListMapper
import dev.kuronosu.deguvon.datasource.network.mapper.DirectoryNetworkModelMapper
import dev.kuronosu.deguvon.datasource.network.mapper.GenericNetworkModelListMapper
import dev.kuronosu.deguvon.model.Anime
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class AnimeRepository(applicationContext: Context) : DataSource(applicationContext) {
    fun getDirectory(callback: DataSourceCallback<List<Anime>>, forceRemote: Boolean = false) {
        callback.onSuccess(getDirectoryFromDB(), DataSourceType.Local)
        if (forceRemote)
            MainScope().launch {
                try {
                    val response = webservice.getDirectory()
                    if (response.isSuccessful && response.body() != null) {
                        val networkDirectory = response.body()!!
                        val directory = DirectoryNetworkModelMapper().map(networkDirectory)
                        db.animeDAO().clearDirectory()
                        val statesMapper = GenericListToStateRoomModelListMapper()
                        val typesMapper = GenericListToTypeRoomModelListMapper()
                        val genresMapper = GenericListToGenreRoomModelListMapper()
                        db.animeDAO().insertDirectory(
                            statesMapper.map(GenericNetworkModelListMapper().map(networkDirectory.states)),
                            typesMapper.map(GenericNetworkModelListMapper().map(networkDirectory.types)),
                            genresMapper.map(GenericNetworkModelListMapper().map(networkDirectory.genres)),
                            AnimeNetworkListToAnimeRoomListMapper().map(networkDirectory.animes)
                        )
                        callback.onSuccess(directory, DataSourceType.Remote)
                    } else {
                        callback.onError(response.message())
                    }
                } catch (e: Exception) {
                    callback.onError(e.message!!)
                }
            }
    }

    private fun getDirectoryFromDB(): List<Anime> {
        val mapper = AnimeRoomModelListMapper(
            db.animeDAO().getStates(),
            db.animeDAO().getTypes(),
            db.animeDAO().getGenres()
        )
        return mapper.map(db.animeDAO().getAnimes())
    }
}