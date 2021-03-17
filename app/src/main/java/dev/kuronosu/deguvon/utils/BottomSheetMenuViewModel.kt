package dev.kuronosu.deguvon.utils

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomSheetMenuViewModel : ViewModel() {
    private val _handler = MutableLiveData<ClickHandler> { _, c ->
        Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show()
    }
    val handler: LiveData<ClickHandler> = _handler

    fun setHandler(value: ClickHandler) {
        _handler.postValue(value)
    }
}