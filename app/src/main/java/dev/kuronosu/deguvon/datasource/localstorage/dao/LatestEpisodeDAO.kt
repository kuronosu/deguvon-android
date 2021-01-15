package dev.kuronosu.deguvon.datasource.localstorage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestAndAnime
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeAnimeDataRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel

@Dao
interface LatestEpisodeDAO {
    @Query("SELECT * FROM latest_episodes")
    fun getAll(): List<LatestAndAnime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLatest(latestEpisodeRoomModel: LatestEpisodeRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLatest(latestEpisodeRoomModelList: List<LatestEpisodeRoomModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLatestAnime(latestEpisodeAnimeDataRoomModel: LatestEpisodeAnimeDataRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLatestAnime(latestEpisodeAnimeDataRoomModelList: List<LatestEpisodeAnimeDataRoomModel>)

    @Query("DELETE FROM latest_episodes")
    fun clearLatest()

    @Query("DELETE FROM latest_episodes_anime")
    fun clearLatestAnime()
}
