package dev.kuronosu.deguvon.datasource

import android.content.Context
import androidx.room.Room
import dev.kuronosu.deguvon.datasource.localstorage.AppDatabase
import dev.kuronosu.deguvon.datasource.network.ApiAdapter

abstract class DataSource(val applicationContext: Context) {
    val webservice = ApiAdapter.API_SERVICE
    val db: AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "deguvon"
    ).allowMainThreadQueries().build()
}
