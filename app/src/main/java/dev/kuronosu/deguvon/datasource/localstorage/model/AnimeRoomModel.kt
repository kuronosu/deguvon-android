package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.kuronosu.deguvon.datasource.localstorage.converters.ListAnimeRelationRoomModelConverter
import dev.kuronosu.deguvon.datasource.localstorage.converters.ListEpisodeRoomModelConverter
import dev.kuronosu.deguvon.datasource.localstorage.converters.ListStringConverter

data class AnimeRelationRoomModel(
    val name: String,
    val url: String,
    val rel: String,
)

data class EpisodeRoomModel(
    val number: String,
    val eid: String,
    val url: String,
    val img: String,
)

@Entity(tableName = "animes")
data class AnimeRoomModel(
    @PrimaryKey val flvid: String,
    val name: String,
    val slug: String,
    @ColumnInfo(name = "next_episode_date") val nextEpisodeDate: String,
    val url: String,
    val state: String,
    val type: String,
    @field:TypeConverters(ListStringConverter::class)
    val genres: List<String>, // Is Many TO Many
    @field:TypeConverters(ListStringConverter::class)
    @ColumnInfo(name = "other_names") val otherNames: List<String>,
    val synopsis: String,
    val score: String,
    val votes: String,
    val cover: String,
    val banner: String,
    @field:TypeConverters(ListEpisodeRoomModelConverter::class) val episodes: List<EpisodeRoomModel>,
    @field:TypeConverters(ListAnimeRelationRoomModelConverter::class) val relations: List<AnimeRelationRoomModel>,
)
