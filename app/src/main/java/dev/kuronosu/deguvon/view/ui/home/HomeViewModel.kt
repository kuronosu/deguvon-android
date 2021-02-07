package dev.kuronosu.deguvon.view.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kuronosu.deguvon.datasource.DataSourceCallback
import dev.kuronosu.deguvon.datasource.DataSourceType
import dev.kuronosu.deguvon.datasource.Repository
import dev.kuronosu.deguvon.model.Generic
import dev.kuronosu.deguvon.model.LatestEpisode

class HomeViewModel : ViewModel() {

    private val _latestEpisodes = MutableLiveData<List<LatestEpisode>>()
    val latestEpisodes: LiveData<List<LatestEpisode>> = _latestEpisodes

    private lateinit var repository: Repository
    private lateinit var context: Context

    private val refreshLatestCallback = object : DataSourceCallback<List<LatestEpisode>> {
        override fun onError(error: String) {
            val le = LatestEpisode("", "", "", Generic(0, ""))
            _latestEpisodes.postValue(
                arrayListOf(
                    le, le, le, le, le, le, le, le, le, le,
                    le, le, le, le, le, le, le, le, le, le
                )
            )
            Toast.makeText(
                context, "Error cargando los últimos episodios\n$error",
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onSuccess(data: List<LatestEpisode>, sourceType: DataSourceType) {
            if (!(data.isEmpty() && sourceType == DataSourceType.Local))
                _latestEpisodes.postValue(data)
        }
    }

    fun refresh(force: Boolean = false) {
        val comp = _latestEpisodes.value?.isEmpty()
        Log.e("KURONOSU", (force || comp == null || comp).toString())
        if (force || comp == null || comp)
            repository.latestEpisodes(refreshLatestCallback)
    }

    fun setUpContext(context: Context) {
        this.context = context
        repository = Repository(context)
    }
}