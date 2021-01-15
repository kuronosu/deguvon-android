package dev.kuronosu.deguvon.datasource.localstorage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kuronosu.deguvon.datasource.localstorage.model.LatestEpisodeRoomModel

@Dao
interface LatestEpisodeDAO {
    @Query("SELECT * FROM latest_episodes")
    fun getAll(): List<LatestEpisodeRoomModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(latestEpisodeRoomModel: LatestEpisodeRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLatest(latestEpisodeRoomModelList: List<LatestEpisodeRoomModel>)

    @Query("DELETE FROM latest_episodes")
    fun clear()
}
