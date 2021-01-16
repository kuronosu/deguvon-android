package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.Entity

@Entity(tableName = "anime_state")
class StateRoomModel(id: String, name: String) : GenericRoomModel(id, name)