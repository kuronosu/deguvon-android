package dev.kuronosu.deguvon.datasource

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import dev.kuronosu.deguvon.datasource.localstorage.AppDatabase
import dev.kuronosu.deguvon.datasource.localstorage.dao.insertDirectory
import dev.kuronosu.deguvon.datasource.localstorage.mapper.*
import dev.kuronosu.deguvon.datasource.mapper.AnimeNetworkListToAnimeRoomListMapper
import dev.kuronosu.deguvon.datasource.network.ApiAdapter
import dev.kuronosu.deguvon.datasource.network.mapper.GenericNetworkModelListMapper
import dev.kuronosu.deguvon.datasource.network.mapper.LatestEpisodeNetworkModelListMapper
import dev.kuronosu.deguvon.model.Anime
import dev.kuronosu.deguvon.model.LatestEpisode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Repository(private val applicationContext: Context) {
    private val webservice = ApiAdapter.API_SERVICE
    private val db: AppDatabase = Room.databaseBuilder(
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
                    if (dbItems.isEmpty() || dbItems[0].url != list[0].url) {
                        Toast.makeText(
                            applicationContext,
                            "Actualizando directorio",
                            Toast.LENGTH_SHORT
                        ).show()
                        CoroutineScope(Dispatchers.IO).launch {
                            updateDirectory(
                                onSuccess = {
                                    MainScope().launch {
                                        Toast.makeText(
                                            applicationContext,
                                            "Directorio actualizado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }, onError = {
                                    MainScope().launch {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error al actualizar el directorio\n$it",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                        }
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

    fun getPagedAnimes(
        limit: Int,
        page: Int,
        onSuccess: (data: List<Anime>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = getDirectoryFromDB(limit, page)
            onSuccess(data)
        }
    }

    fun searchAnime(
        search: String,
        limit: Int,
        page: Int,
        onSuccess: (result: List<Anime>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            onSuccess(searchAnimeFromDB(search.trim(), limit, page))
        }
    }

    fun getAnimeCount(): Int {
        return db.animeDAO().getAnimeCount()
    }

    fun getAnimeSearchCount(search: String): Int {
        return if (search.isEmpty()) getAnimeCount()
        else db.animeDAO().getAnimeSearchCount(search.trim())
    }

    suspend fun updateDirectory(onSuccess: () -> Unit, onError: (error: String) -> Unit) {
        try {
            val response = webservice.getDirectory()
            if (response.isSuccessful && response.body() != null) {
                val networkDirectory = response.body()!!
                val statesMapper = GenericListToStateRoomModelListMapper()
                val typesMapper = GenericListToTypeRoomModelListMapper()
                val genresMapper = GenericListToGenreRoomModelListMapper()
                db.animeDAO().insertDirectory(
                    statesMapper.map(GenericNetworkModelListMapper().map(networkDirectory.states)),
                    typesMapper.map(GenericNetworkModelListMapper().map(networkDirectory.types)),
                    genresMapper.map(GenericNetworkModelListMapper().map(networkDirectory.genres)),
                    AnimeNetworkListToAnimeRoomListMapper().map(networkDirectory.animes)
                )
                onSuccess()
            } else {
                onError("Unsuccessful answer")
            }
        } catch (e: Exception) {
            onError(e.message!!)
        }

    }

    private fun getDirectoryFromDB(limit: Int, page: Int): List<Anime> {
        val mapper = AnimeRoomModelListMapper(
            db.animeDAO().getStates(),
            db.animeDAO().getTypes(),
            db.animeDAO().getGenres()
        )
        return mapper.map(db.animeDAO().getAnimesByPages(limit, page * limit))
    }

    private fun searchAnimeFromDB(search: String, limit: Int, page: Int): List<Anime> {
        val mapper = AnimeRoomModelListMapper(
            db.animeDAO().getStates(),
            db.animeDAO().getTypes(),
            db.animeDAO().getGenres()
        )
        return mapper.map(
            if (search.isEmpty()) db.animeDAO().getAnimesByPages(limit, page * limit)
            else db.animeDAO().searchAnimesByPages(search, limit, page * limit)
        )
    }
}
