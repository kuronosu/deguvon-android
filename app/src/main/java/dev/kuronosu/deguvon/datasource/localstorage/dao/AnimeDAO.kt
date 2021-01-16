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

    @Query("select * FROM anime")
    fun getAnimes(): List<AnimeRoomModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertState(state: StateRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: GenreRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(type: TypeRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStates(states: List<StateRoomModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreRoomModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(types: List<TypeRoomModel>)
}

fun AnimeDAO.insertAll(
    states: List<StateRoomModel>,
    genres: List<GenreRoomModel>,
    types: List<TypeRoomModel>
) {
    this.insertStates(states)
    this.insertGenres(genres)
    this.insertTypes(types)
}
