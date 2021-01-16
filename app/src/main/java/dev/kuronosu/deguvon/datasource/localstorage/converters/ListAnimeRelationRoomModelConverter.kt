package dev.kuronosu.deguvon.datasource.localstorage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.kuronosu.deguvon.datasource.localstorage.model.AnimeRelationRoomModel

class ListAnimeRelationRoomModelConverter {
    @TypeConverter
    fun toJsonString(data: List<AnimeRelationRoomModel>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJsonString(jsonList: String): List<AnimeRelationRoomModel> {
        return Gson().fromJson(jsonList, object : TypeToken<List<AnimeRelationRoomModel>>() {}.type)
    }
}