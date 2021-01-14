package dev.kuronosu.deguvon.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kuronosu.deguvon.model.LatestEpisode
import dev.kuronosu.deguvon.network.ApiAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class HomeViewModel : ViewModel(), CoroutineScope by MainScope() {

    private val _latestEpisodes = MutableLiveData<List<LatestEpisode>>()
    val latestEpisodes: LiveData<List<LatestEpisode>> = _latestEpisodes
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchLatestEpisodes()
    }

    private fun fetchLatestEpisodes() {
        async {
            processStarted()
            try {
                val response = ApiAdapter.apiClient.getLatest()
                if (response.isSuccessful && response.body() != null) {
                    val items = response.body()!!
                    _latestEpisodes.postValue(items)
                }
            } catch (e: Exception) {
            }
            processFinished()
        }
    }

    private fun processStarted() {
        isLoading.value = true
    }

    private fun processFinished() {
        isLoading.value = false
    }
}