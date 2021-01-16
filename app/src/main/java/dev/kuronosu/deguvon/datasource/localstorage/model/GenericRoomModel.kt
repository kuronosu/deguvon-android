package dev.kuronosu.deguvon.datasource.localstorage.model

import androidx.room.PrimaryKey

open class GenericRoomModel(
    @PrimaryKey val id: String,
    val name: String
)
