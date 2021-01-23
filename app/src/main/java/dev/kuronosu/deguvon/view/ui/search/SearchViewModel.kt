package dev.kuronosu.deguvon.view.ui.search

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kuronosu.deguvon.datasource.AnimeRepository
import dev.kuronosu.deguvon.datasource.DataSourceCallback
import dev.kuronosu.deguvon.datasource.DataSourceType
import dev.kuronosu.deguvon.model.Anime

class SearchViewModel : ViewModel() {
    private val pageCount = 30
    private val animeList = ArrayList<Anime>()
    private var page = 0
    private val _animes = MutableLiveData<List<Anime>>()
    val animes: LiveData<List<Anime>> = _animes

    private lateinit var repository: AnimeRepository
    private lateinit var context: Context
    private var totalAnime: Int = 0
    private var isLoading = false

    private val callback = object : DataSourceCallback<List<Anime>> {
        override fun onError(error: String) {
            Toast.makeText(
                context,
                "Error al cargar animes",
                Toast.LENGTH_SHORT
            ).show()
            isLoading = false
        }

        override fun onSuccess(data: List<Anime>, sourceType: DataSourceType) {
            page++
            isLoading = false
            animeList.addAll(data)
            _animes.postValue(animeList)
        }
    }

    fun loadAnimes() {
        if (!isLoading && animeList.size < totalAnime) {
            isLoading = true
            repository.getPagedAnimes(pageCount, page, callback)
        }
    }

    fun setUpContext(context: Context) {
        this.context = context
        repository = AnimeRepository(context)
        totalAnime = repository.getAnimeCount()
    }
}