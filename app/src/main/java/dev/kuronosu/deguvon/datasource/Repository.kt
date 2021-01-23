package dev.kuronosu.deguvon.datasource

import android.content.Context
import androidx.room.Room
import dev.kuronosu.deguvon.datasource.localstorage.AppDatabase
import dev.kuronosu.deguvon.datasource.localstorage.dao.insertDirectory
import dev.kuronosu.deguvon.datasource.localstorage.mapper.*
import dev.kuronosu.deguvon.datasource.mapper.AnimeNetworkListToAnimeRoomListMapper
import dev.kuronosu.deguvon.datasource.network.ApiAdapter
import dev.kuronosu.deguvon.datasource.network.mapper.DirectoryNetworkModelMapper
import dev.kuronosu.deguvon.datasource.network.mapper.GenericNetworkModelListMapper
import dev.kuronosu.deguvon.datasource.network.mapper.LatestEpisodeNetworkModelListMapper
import dev.kuronosu.deguvon.model.Anime
import dev.kuronosu.deguvon.model.LatestEpisode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Repository(private val applicationContext: Context) {
    val webservice = ApiAdapter.API_SERVICE
    val db: AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "deguvon"
    ).allowMainThreadQueries().build()

    fun latestEpisodes(callback: DataSourceCallback<List<LatestEpisode>>) {
        val dbItems = db.latestEpisodesDAO().getAll()
        callback.onSuccess(
            LatestEpisodeRoomModelListMapper().map(dbItems),
            DataSourceType.Local
        )
        MainScope().launch {
            try {
                val response = webservice.getLatestEpisodes()
                if (response.isSuccessful && response.body() != null) {
                    val items = response.body()!!
                    val list = LatestEpisodeNetworkModelListMapper().map(items)
                    db.latestEpisodesDAO().let {
                        it.clear()
                        it.insertAllLatest(
                            LatestEpisodeToLatestEpisodeRoomModelListMapper().map(
                                list
                            )
                        )
                    }
                    callback.onSuccess(list, DataSourceType.Remote)
                } else {
                    if (dbItems.isEmpty()) {
                        callback.onError(response.message())
                    }
                }
            } catch (e: Exception) {
                if (dbItems.isEmpty()) {
                    callback.onError(e.message!!)
                }
            }
        }
    }

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
