package dev.kuronosu.deguvon.datasource.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.kuronosu.deguvon.datasource.localstorage.dao.LatestEpisodeDAO
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel

@Database(
    version = 1,
    entities = [LatestEpisodeRoomModel::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun latestEpisodesDAO(): LatestEpisodeDAO
}