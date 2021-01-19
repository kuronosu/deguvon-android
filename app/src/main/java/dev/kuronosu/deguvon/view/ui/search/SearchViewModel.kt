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

    private val _text = MutableLiveData<String>().apply {
        value = "Animes obtenido: 0"
    }
    val text: LiveData<String> = _text

    fun downloadDirectory(context: Context) {
        AnimeRepository(context).getDirectory(object : DataSourceCallback<List<Anime>> {
            override fun onError(error: String) {
                Toast.makeText(
                    context,
                    "Error descargando el directorio\n$error",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onSuccess(data: List<Anime>, sourceType: DataSourceType) {
                _text.postValue("Animes obtenido: ${data.size}")
            }
        }, true)
    }
}