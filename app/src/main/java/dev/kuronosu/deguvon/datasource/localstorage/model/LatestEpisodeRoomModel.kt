package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class LatestEpisodeAnimeDataRoomModel(
    val aid: String,
    val name: String
)

@Entity(tableName = "latest_episodes")
data class LatestEpisodeRoomModel(
    @PrimaryKey val url: String,
    val image: String,
    val number: String,
    @Embedded val anime: LatestEpisodeAnimeDataRoomModel
)

/*data class LatestAndAnime(
    @Embedded val latest: LatestEpisodeRoomModel,
    @Relation(
        parentColumn = "url",
        entityColumn = "episodeDBId",
        entity = LatestEpisodeAnimeDataRoomModel::class
    )
    val anime: LatestEpisodeAnimeDataRoomModel
)*/
