package dev.kuronosu.deguvon.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.datasource.DataSourceCallback
import dev.kuronosu.deguvon.datasource.DataSourceType
import dev.kuronosu.deguvon.datasource.Repository
import dev.kuronosu.deguvon.datasource.getRepository
import dev.kuronosu.deguvon.datasource.model.Generic
import dev.kuronosu.deguvon.datasource.model.LatestEpisode
import dev.kuronosu.deguvon.ui.animeDetails.KEY_ANIME

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
        val comp = _latestEpisodes.value?.isEmpty() ?: true
        Log.e("KURONOSU", (force || comp).toString())
        if (force || comp)
            repository.latestEpisodes(refreshLatestCallback)
    }

    fun setUpContext(context: Context) {
        this.context = context
        repository = getRepository(context)
    }

    fun onNavigateToAnime(episode: LatestEpisode, navController: NavController): Boolean {
        val anime = repository.findAnime(episode.anime.id)
        if (anime != null) {
            val bundle = bundleOf(KEY_ANIME to anime)
            navController.navigate(R.id.navigation_anime_details, bundle)
            return true
        }
        Toast.makeText(context, "El anime no esta en la base de datos", Toast.LENGTH_SHORT).show()
        return false
    }
}