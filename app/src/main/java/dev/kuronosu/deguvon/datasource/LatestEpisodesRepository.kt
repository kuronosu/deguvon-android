package dev.kuronosu.deguvon.datasource

import android.content.Context
import dev.kuronosu.deguvon.datasource.localstorage.mapper.LatestEpisodeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.localstorage.mapper.LatestEpisodeToLatestEpisodeRoomModelListMapper
import dev.kuronosu.deguvon.datasource.network.mapper.LatestEpisodeNetworkModelListMapper
import dev.kuronosu.deguvon.model.LatestEpisode
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LatestEpisodesRepository(applicationContext: Context) : DataSource(applicationContext) {

    fun getAll(callback: DataSourceCallback<List<LatestEpisode>>) {
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
}
