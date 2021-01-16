package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.Entity

@Entity(tableName = "anime_type")
class TypeRoomModel(id: String, name: String) : GenericRoomModel(id, name)
