package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.Entity

@Entity(tableName = "anime_genre")
class GenreRoomModel(id: String, name: String) : GenericRoomModel(id, name)