package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.kuronosu.deguvon.datasource.model.Generic
import dev.kuronosu.deguvon.datasource.model.LatestEpisode


data class LatestEpisodeAnimeDataRoomModel(
    val id: String,
    val name: String
)

@Entity(tableName = "latest_episodes")
data class LatestEpisodeRoomModel(
    @PrimaryKey val url: String,
    val image: String,
    val number: String,
    @Embedded val anime: LatestEpisodeAnimeDataRoomModel
)

fun LatestEpisodeRoomModel.asInterfaceModel() = LatestEpisode(
    url = url,
    image = image,
    capi = number,
    anime = Generic(anime.id.toInt(), anime.name)
)

fun List<LatestEpisodeRoomModel>.asInterfaceModel() = map { it.asInterfaceModel() }

/*data class LatestAndAnime(
    @Embedded val latest: LatestEpisodeRoomModel,
    @Relation(
        parentColumn = "url",
        entityColumn = "episodeDBId",
        entity = LatestEpisodeAnimeDataRoomModel::class
    )
    val anime: LatestEpisodeAnimeDataRoomModel
)*/
