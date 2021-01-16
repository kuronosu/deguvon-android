package dev.kuronosu.deguvon.datasource.localstorage

import androidx.room.Database
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