package dev.kuronosu.deguvon.view.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kuronosu.deguvon.datasource.DataSourceCallback
import dev.kuronosu.deguvon.datasource.LatestEpisodesRepository
import dev.kuronosu.deguvon.model.LatestEpisode

class HomeViewModel : ViewModel() {

    private val _latestEpisodes = MutableLiveData<List<LatestEpisode>>()
    val latestEpisodes: LiveData<List<LatestEpisode>> = _latestEpisodes

    fun refresh(context: Context) {
        LatestEpisodesRepository(context).getAll(object : DataSourceCallback<List<LatestEpisode>> {
            override fun onError(error: String) {
                Toast.makeText(
                    context,
                    "Error cargando los últimos episodios\n$error",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onSuccess(t: List<LatestEpisode>) {
                _latestEpisodes.postValue(t)
            }
        })
    }
}