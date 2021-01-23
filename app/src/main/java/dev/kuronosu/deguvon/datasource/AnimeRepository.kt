package dev.kuronosu.deguvon.datasource

import android.content.Context
import dev.kuronosu.deguvon.datasource.localstorage.dao.insertDirectory
import dev.kuronosu.deguvon.datasource.localstorage.mapper.AnimeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToGenreRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToStateRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.GenericListToTypeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.mapper.AnimeNetworkListToAnimeRoomListMapper
import dev.kuronosu.deguvon.datasource.network.mapper.DirectoryNetworkModelMapper
import dev.kuronosu.deguvon.datasource.network.mapper.GenericNetworkModelListMapper
import dev.kuronosu.deguvon.model.Anime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AnimeRepository(applicationContext: Context) : DataSource(applicationContext) {
    fun getPagedAnimes(limit: Int, page: Int, callback: DataSourceCallback<List<Anime>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = getDirectoryFromDB(limit, page)
            callback.onSuccess(data, DataSourceType.Local)
        }
    }

    fun updateDirectory(callback: DataSourceCallback<List<Anime>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = webservice.getDirectory()
                if (response.isSuccessful && response.body() != null) {
                    val networkDirectory = response.body()!!
                    val directory = DirectoryNetworkModelMapper().map(networkDirectory)
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

    fun getAnimeCount(): Int {
        return db.animeDAO().getAnimeCount()
    }

    private fun getDirectoryFromDB(limit: Int, page: Int): List<Anime> {
        val mapper = AnimeRoomModelListMapper(
            db.animeDAO().getStates(),
            db.animeDAO().getTypes(),
            db.animeDAO().getGenres()
        )
        return mapper.map(db.animeDAO().getAnimesByPages(limit, page * limit))
    }
}