package dev.kuronosu.deguvon.view.ui.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.kuronosu.deguvon.datasource.Repository
import dev.kuronosu.deguvon.model.Anime

class SearchViewModel : ViewModel() {
    private val pageCount = 30
    private val animeList = ArrayList<Anime>()
    private var page = 0
    private val _animes = MutableLiveData<List<Anime>>()
    val animes: LiveData<List<Anime>> = _animes

    private lateinit var repository: Repository
    private lateinit var context: Context
    private var totalAnime: Int = 0
    private var isLoading = false
    private var searchString = ""

    fun searchAnimes(search: String) {
        if (searchString != search) {
            totalAnime = repository.getAnimeSearchCount(search)
            page = 0
            searchString = search
            animeList.clear()
            isLoading = false
            if (totalAnime == 0)
                _animes.postValue(animeList)
            else
                loadMore()
        }
    }

    fun initialLoad() {
        if (animeList.isEmpty())
            _loadMore()
    }

    fun loadMore() {
        if (animeList.size < totalAnime)
            _loadMore()
    }

    private fun _loadMore() {
        if (!isLoading) {
            isLoading = true
            repository.searchAnime(searchString, pageCount, page) {
                if (isLoading) {
                    page += 1
                    isLoading = false
                    animeList.addAll(it)
                    _animes.postValue(animeList)
                }
            }
        }
    }

    fun setUpContext(context: Context) {
        this.context = context
        repository = Repository(context)
        totalAnime = repository.getAnimeCount()
        isLoading = false;
    }
}