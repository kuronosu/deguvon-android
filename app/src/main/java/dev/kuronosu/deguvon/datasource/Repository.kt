package dev.kuronosu.deguvon.datasource

import android.content.Context
import android.util.Log
import android.widget.Toast
import dev.kuronosu.deguvon.datasource.localstorage.AppDatabase
import dev.kuronosu.deguvon.datasource.localstorage.dao.insertDirectory
import dev.kuronosu.deguvon.datasource.localstorage.getDatabase
import dev.kuronosu.deguvon.datasource.localstorage.model.asInterfaceModel
import dev.kuronosu.deguvon.datasource.model.Anime
import dev.kuronosu.deguvon.datasource.model.LatestEpisode
import dev.kuronosu.deguvon.datasource.network.ApiAdapter
import dev.kuronosu.deguvon.datasource.network.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Repository(private val applicationContext: Context) {
    private val webservice = ApiAdapter.API_SERVICE
    private val db: AppDatabase = getDatabase(applicationContext, true)

    private var dbStates = db.animeDAO().getStates()
    private var dbTypes = db.animeDAO().getTypes()
    private var dbGenres = db.animeDAO().getGenres()

    fun latestEpisodes(callback: DataSourceCallback<List<LatestEpisode>>) {
        val dbLatest = db.latestEpisodesDAO().getAll()
        callback.onSuccess(
            dbLatest.asInterfaceModel(),
            DataSourceType.Local
        )
        MainScope().launch {
            try {
                val response = webservice.getLatestEpisodes()
                if (response.isSuccessful && response.body() != null) {
                    val items = response.body()!!
                    db.latestEpisodesDAO().let {
                        it.clear()
                        it.insertAllLatest(items.asRoomModel())
                    }
                    if (dbLatest.isEmpty() || dbLatest[0].url != items[0].url || getAnimeCount() == 0) {
                        Toast.makeText(
                            applicationContext,
                            "Actualizando directorio",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("AD", dbGenres.size.toString())
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
                    callback.onSuccess(items.asInterfaceModel(), DataSourceType.Remote)
                } else {
                    if (dbLatest.isEmpty()) {
                        callback.onError(response.message())
                    }
                }
            } catch (e: Exception) {
                if (dbLatest.isEmpty()) {
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
                db.animeDAO().insertDirectory(
                    networkDirectory.states.asStateRoomModel(),
                    networkDirectory.types.asTypeRoomModel(),
                    networkDirectory.genres.asGenreRoomModel(),
                    networkDirectory.animes.asRoomModel()
                )
                updateLocalVariables()
                onSuccess()
            } else {
                onError("Unsuccessful answer")
            }
        } catch (e: Exception) {
            onError(e.message!!)
        }
    }

    private fun getDirectoryFromDB(limit: Int, page: Int) =
        db.animeDAO().getAnimesByPages(limit, page * limit)
            .asInterfaceModel(dbStates, dbTypes, dbGenres)


    private fun searchAnimeFromDB(search: String, limit: Int, page: Int): List<Anime> {
        val list = if (search.isEmpty()) db.animeDAO().getAnimesByPages(limit, page * limit)
        else db.animeDAO().searchAnimesByPages(search, limit, page * limit)
        return list.asInterfaceModel(dbStates, dbTypes, dbGenres)
    }

    private fun updateLocalVariables() {
        dbStates = db.animeDAO().getStates()
        dbTypes = db.animeDAO().getTypes()
        dbGenres = db.animeDAO().getGenres()
    }
}

private lateinit var INSTANCE: Repository

fun getRepository(context: Context): Repository {
    synchronized(Repository::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Repository(context)
        }
    }
    return INSTANCE
}
