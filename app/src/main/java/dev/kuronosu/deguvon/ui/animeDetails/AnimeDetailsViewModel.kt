package dev.kuronosu.deguvon.ui.animeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnimeDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Anime details"
    }
    val text: LiveData<String> = _text
}