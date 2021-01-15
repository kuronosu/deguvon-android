package dev.kuronosu.deguvon.datasource

import android.content.Context
import dev.kuronosu.deguvon.datasource.localstorage.mapper.LatestEpisodeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.LatestEpisodeToLatestEpisodeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeAnimeDataRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel
import dev.kuronosu.deguvon.datasource.network.mapper.LatestEpisodeNetworkModelListMapper
import dev.kuronosu.deguvon.model.LatestEpisode
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LatestEpisodesRepository(applicationContext: Context) :
    DataSource<LatestEpisode, String>(applicationContext) {

    override fun getAll(callback: DataSourceCallback<List<LatestEpisode>>) {
        MainScope().launch {
            val dbItems = db.userDao().getAll()
            callback.onSuccess(LatestEpisodeRoomModelListMapper().map(dbItems))
            try {
                val response = webservice.getLatestEpisodes()
                if (response.isSuccessful && response.body() != null) {
                    val items = response.body()!!
                    val list = LatestEpisodeNetworkModelListMapper().map(items)
                    val latestList = ArrayList<LatestEpisodeRoomModel>()
                    val latestAnimeList = ArrayList<LatestEpisodeAnimeDataRoomModel>()
                    LatestEpisodeToLatestEpisodeRoomModelListMapper().map(list).forEach {
                        latestList.add(it.latest)
                        latestAnimeList.add(it.anime)
                    }
                    db.userDao().let {
                        it.clearLatestAnime()
                        it.clearLatest()
                        it.insertAllLatest(latestList)
                        it.insertAllLatestAnime(latestAnimeList)
                    }
                    callback.onSuccess(list)
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
}
