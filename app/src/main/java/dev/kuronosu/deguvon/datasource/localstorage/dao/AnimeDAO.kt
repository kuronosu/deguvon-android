package dev.kuronosu.deguvon.datasource.localstorage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.GenreRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.StateRoomModel
import dev.kuronosu.deguvon.datasource.localstorage.model.TypeRoomModel


@Dao
interface AnimeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: AnimeRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimes(animes: List<AnimeRoomModel>)

    @Query("select * FROM animes ORDER BY name LIMIT :limit OFFSET :offset ")
    fun getAnimesByPages(limit: Int, offset: Int): List<AnimeRoomModel>

    @Query("select * FROM animes WHERE (name LIKE '%' || :search || '%' OR other_names LIKE '%' || :search || '%') ORDER BY name LIMIT :limit OFFSET :offset ")
    fun searchAnimesByPages(search: String, limit: Int, offset: Int): List<AnimeRoomModel>

    @Query("select * FROM animes where flvid LIKE :aid")
    fun getAnimeByID(aid: String): AnimeRoomModel?

    @Query("DELETE FROM animes")
    fun clearAnimes()

    @Query("SELECT COUNT(flvid) FROM animes")
    fun getAnimeCount(): Int

    @Query("SELECT COUNT(flvid) FROM animes WHERE name LIKE '%' || :search || '%' OR other_names LIKE '%' || :search || '%' ")
    fun getAnimeSearchCount(search: String): Int

    // Genres
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: GenreRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreRoomModel>)

    @Query("select * FROM anime_genre")
    fun getGenres(): List<GenreRoomModel>

    @Query("select * FROM anime_genre where id LIKE :id")
    fun getGenreByID(id: String): GenreRoomModel

    @Query("select * FROM anime_genre where id IN(:ids)")
    fun getGenresByIDs(ids: List<String>): List<GenreRoomModel>

    @Query("DELETE FROM anime_genre")
    fun clearGenres()

    // States
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertState(state: StateRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStates(states: List<StateRoomModel>)

    @Query("select * FROM anime_state")
    fun getStates(): List<StateRoomModel>

    @Query("select * FROM anime_state where id LIKE :id")
    fun getStateByID(id: String): StateRoomModel

    @Query("DELETE FROM anime_state")
    fun clearStates()

    // Types
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(type: TypeRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(types: List<TypeRoomModel>)

    @Query("select * FROM anime_type")
    fun getTypes(): List<TypeRoomModel>

    @Query("select * FROM anime_type where id LIKE :id")
    fun getTypesByID(id: String): TypeRoomModel

    @Query("DELETE FROM anime_type")
    fun clearTypes()
}

fun AnimeDAO.clearDirectory() {
    this.clearAnimes()
    this.clearGenres()
    this.clearStates()
    this.clearTypes()
}

fun AnimeDAO.insertDirectory(
    states: List<StateRoomModel>,
    types: List<TypeRoomModel>,
    genres: List<GenreRoomModel>,
    animes: List<AnimeRoomModel>
) {
    this.insertStates(states)
    this.insertTypes(types)
    this.insertGenres(genres)
    this.insertAnimes(animes)
}
