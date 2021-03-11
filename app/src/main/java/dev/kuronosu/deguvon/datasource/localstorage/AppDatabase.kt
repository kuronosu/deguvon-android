package dev.kuronosu.deguvon.datasource.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.kuronosu.deguvon.datasource.localstorage.dao.AnimeDAO
import dev.kuronosu.deguvon.datasource.localstorage.dao.LatestEpisodeDAO
import dev.kuronosu.deguvon.datasource.localstorage.model.*

@Database(
    version = 1,
    entities = [LatestEpisodeRoomModel::class, StateRoomModel::class, GenreRoomModel::class,
        TypeRoomModel::class, AnimeRoomModel::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun latestEpisodesDAO(): LatestEpisodeDAO
    abstract fun animeDAO(): AnimeDAO
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context, allowMainThreadQueries: Boolean): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            var tmp = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "deguvon"
            )
            if (allowMainThreadQueries) tmp = tmp.allowMainThreadQueries()
            INSTANCE = tmp.build()
        }
    }
    return INSTANCE
}
